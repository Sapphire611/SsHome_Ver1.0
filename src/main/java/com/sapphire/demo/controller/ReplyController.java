package com.sapphire.demo.controller;

import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.ReplyMapper;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.Reply;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.ViewRecord;
import com.sapphire.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/1 5:18
 */
@Controller
public class ReplyController {

	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private QuestionMapper questionMapper;

	
	@PostMapping("/reply")
	public String questionReply(@RequestParam(name = "questionId",required = false) Integer questionId,
								@RequestParam(name = "userId",required = false) Integer userId,
								@RequestParam(name = "description",required = false) String description,
								HttpServletRequest request, 
								Model model) {
		
		Reply reply = new Reply();
		reply.setQuestionId(questionId);
		reply.setDescription(description);
		//reply.setDescription(description.replaceAll(" ","").replaceAll("\r","<br/>"));
		reply.setUserId(userId);
		reply.setGmtCreate(System.currentTimeMillis());
		reply.setGmtModified(System.currentTimeMillis());
		
		// System.out.println(reply.toString());
		
		Boolean description_null = reply.getDescription().equals("");
		
		if(description_null == false) {
			replyMapper.insert(reply); // 加入Reply Table
			questionMapper.commentAdd(questionId);
			
		}
		
		return "redirect:/question/" + questionId + "";
	}

}
