package com.sbs.example.mysqlTextBoard.dto;

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
	
}
