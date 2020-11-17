package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class Container {

	public static Scanner scanner;
	public static Session session;
	
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	
	public static MemberService memberService;
	public static ArticleService articleService;
	
	static {
		scanner = new Scanner(System.in);
		session = new Session();
		
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		
		memberService = new MemberService();
		articleService = new ArticleService();
	}

	

}
