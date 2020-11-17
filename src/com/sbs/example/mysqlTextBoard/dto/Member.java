package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {
	public int id;
	public String loginId;
	public String loginPw;
	public String name;
	
	public Member() {
		
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", loginId=" + loginId + ", loginPw=" + loginPw + ", name=" + name + "]";
	}

	public Member(int id, String loginId, String loginPw, String name) {
		this.id = id;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}

	public Member(Map<String, Object> memberlogin) {
		this.id = (int)memberlogin.get("id");
		this.loginId = (String)memberlogin.get("loginId");
		this.loginPw = (String)memberlogin.get("loginPw");
		this.name = (String)memberlogin.get("name");
	}
	
}
