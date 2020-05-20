package com.sapphire.demo.controller;

import com.sapphire.demo.dto.AccessTokenDTO;
import com.sapphire.demo.dto.GithubUser;
import com.sapphire.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                        @RequestParam(name="state") String state,
                        HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(); // 通过accesstoken借口得到accesstoken
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);// 通过accesstoken得到user
        // System.out.println("Username = " + user.getName());

        if(user != null){
            // Cookie & Session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            // Login failed
            return "redirect:/";
        }
    }
}
