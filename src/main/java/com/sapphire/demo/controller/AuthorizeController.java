package com.sapphire.demo.controller;

import com.sapphire.demo.dto.AccessTokenDTO;
import com.sapphire.demo.dto.GithubUser;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.provider.GithubProvider;
import com.sapphire.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirect.uri}")
    private String clientRedirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                        @RequestParam(name="state") String state,
                        HttpServletRequest request,
                        HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(); // 通过accesstoken借口得到accesstoken
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);// 通过accesstoken得到user
        // System.out.println("Username = " + githubUser.getName());

        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            
            //为了得到GitHub中能够对应搜索到的名字
            user.setName(githubUser.getLogin());
            // user.setName(githubUser.getName());
            
            user.setAccountId(String.valueOf(githubUser.getId())); //Long -> String 强制转换
            user.setPassword((int)(Math.random()*9+1)*100000+"");
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setBio(githubUser.getBio());
            userService.createOrUpdate(user);

            // Cookie & Session
            response.addCookie(new Cookie("token",token)); // 把Token放入Cookie中




            request.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }else{
            // Login failed
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
