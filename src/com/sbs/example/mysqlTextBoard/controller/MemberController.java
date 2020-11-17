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

		}
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

		memberService.join(loginId, loginPw, name);

	}

	private void login() {
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
