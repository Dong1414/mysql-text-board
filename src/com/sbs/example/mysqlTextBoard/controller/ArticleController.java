package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController {
	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {

			showList();

		} else if (cmd.equals("article add")) {
			doWrite();
		} else if (cmd.startsWith("article delete ")) {

			int input = Integer.parseInt(cmd.split(" ")[2]);
			doDelete(input);

		} else if (cmd.startsWith("article modify ")) {

			int input = Integer.parseInt(cmd.split(" ")[2]);

			doModify(input);

		} else if (cmd.startsWith("article detail ")) {

			int input = Integer.parseInt(cmd.split(" ")[2]);

			showDetail(input);

		}
	}

	private void doWrite() {
		
		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		Scanner scan = Container.scanner;
		
		String title;
		String body;
		System.out.printf("제목: ");
		title = scan.nextLine(); 
		
		System.out.printf("내용:");
		body = scan.nextLine();
		
		int i = articleService.articleWrite(title,body,Container.session.loginIdSave);
		
	}

	private void showDetail(int input) {

		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		Article article = articleService.getArticle(input);

		if (article == null) {
			System.out.println("존재하지 않는 게시물");
			return;
		}
		else if(article.memberId != Container.session.loginIdSave) {
			System.out.println("게시물 수정은 작성자만 할 수 있습니다.");
			return;
		}
		
		System.out.println("번호: " + article.id);
		System.out.println("날짜: " + article.regDate);
		System.out.println("수정일: " + article.updateDate);
		System.out.println("제목: " + article.title);
		System.out.println("내용: " + article.body);
		System.out.println("작성자: " + article.memberId);
		System.out.println("게시판: " + article.boardId);
	}

	private void doModify(int input) {

		Article articles = articleService.getArticle(input);

		if (articles == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}

		articleService.modify(input);
		System.out.println(input + "번째 게시글을 수정하였습니다.");
	}

	private void doDelete(int input) {
		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		Article article = articleService.getArticle(input);

		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}			
		else if(article.memberId != Container.session.loginIdSave) {
			System.out.println(Container.session.loginIdSave);
			
			System.out.println("게시물 수정은 작성자만 할 수 있습니다.");
			return;
		}

		articleService.delete(input);
		System.out.println(input + "번재 게시글 삭제");
	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목");

		for (Article article : articles) {
			Member member = Container.memberService.getLoginId(article.memberId);
			if(member != null) {
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.regDate, article.updateDate,
					member.name, article.title);
			}else System.out.println("멤버 못가져옴");
		}
	}

}
