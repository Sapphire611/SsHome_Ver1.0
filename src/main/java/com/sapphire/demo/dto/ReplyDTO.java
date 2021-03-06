package com.sapphire.demo.dto;

import com.sapphire.demo.model.User;

public class ReplyDTO {
	private Integer id;
	private Integer questionId;
	private String questionTitle;
	private Integer userId;
	private String description;
	private Long gmtCreate;
	private Long gmtModified;
	private Long gmtQuestionRead;
	private User user;
	
	
	public Long getGmtQuestionRead() {
		return gmtQuestionRead;
	}
	public void setGmtQuestionRead(Long gmtQuestionRead) {
		this.gmtQuestionRead = gmtQuestionRead;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Long getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
