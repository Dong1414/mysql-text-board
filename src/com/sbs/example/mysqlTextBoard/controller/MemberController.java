package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController {

	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			join();
		} else if (cmd.equals("member login")) {
			login();
		} else if(cmd.equals("member logout")) {
			logout();
		} else if(cmd.equals("member whoami")) {
			whoami();
		}
			
	}

	private void whoami() {
		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		Member member = memberService.getLoginId(Container.session.loginIdSave);
		System.out.println("로그인 아이디 : " + member.loginId);
		System.out.println("이름 : " + member.name);
		
		
	}

	private void logout() {
		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		Member member = memberService.getLoginId(Container.session.loginIdSave);
		System.out.println(member.name + "님이 로그아웃 하였습니다.");
		Container.session.loginIdSave = 0;
	}

	private void join() {
		String loginId;
		String loginPw;
		String name;

		Scanner scan = Container.scanner;
		System.out.printf("로그인 아이디: ");
		loginId = scan.nextLine();

		System.out.printf("로그인 비번: ");
		loginPw = scan.nextLine();

		System.out.printf("이름: ");
		name = scan.nextLine();

		int i = memberService.join(loginId, loginPw, name);

		System.out.println(i + "번째 회원가입");

	}

	private void login() {
		if(Container.session.isLoginId()) {
			System.out.println("이미 로그인 중입니다.");
			return;
		}
		String loginId;
		String loginPw;
		Member member = null;

		Scanner scan = Container.scanner;
		System.out.printf("로그인 아이디: ");
		loginId = scan.nextLine();

		member = memberService.login(loginId);
		if (member == null) {
			System.out.println("아이디가 존재하지 않습니다.");
			return;
		}

		System.out.printf("로그인 비번: ");
		loginPw = scan.nextLine();

		if (!member.loginPw.equals(loginPw)) {
			System.out.println("비밀번호가 존재하지 않습니다.");
			return;
		}
		System.out.println("환영합니다 " + member.name + "님");
		Container.session.loginIdSave = member.id;
	}

}
