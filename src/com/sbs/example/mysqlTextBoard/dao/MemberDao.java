package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class MemberDao {

	public void join(String loginId, String loginPw, String name) {
		MysqlUtil.insert(new SecSql().append(
				"INSERT INTO `member` SET loginId = ?, loginPw = ?, `name` = ?, regDate = NOW(), updateDate = NOW()",
				loginId, loginPw, name));

	}

	public Member login(String loginId) {
		Member member = null;
		
		Map<String, Object> memberlogin = MysqlUtil.selectRow(new SecSql().append("select * from `member` where loginId = ?",loginId));
		
		if(memberlogin != null) {
			member = new Member();
			member.id = (int)memberlogin.get("id");
			member.loginId = (String)memberlogin.get("loginId");
			member.loginPw = (String)memberlogin.get("loginPw");
			member.name = (String)memberlogin.get("name");
			
			return member;
		}						
		return member;
	}

	public Member getLoginId(int memberId) {
		Member member = null;
		
		Map<String, Object> memberlogin = MysqlUtil.selectRow(new SecSql().append("select * from `member` where id = ?",memberId));
		
		if(memberlogin != null) {
			member = new Member();
			member.id = (int)memberlogin.get("id");
			member.loginId = (String)memberlogin.get("loginId");
			member.loginPw = (String)memberlogin.get("loginPw");
			member.name = (String)memberlogin.get("name");
			
			return member;
		}				
						
		return member;
	}

}
