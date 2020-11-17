package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
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
		} else if(cmd.equals("article makeboard")){
			doMakeBoard();
		} else if(cmd.startsWith("article selectboard ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doSelectBoard(input);
		} else if(cmd.startsWith("article reple ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doReple(input);
		}
	}

	private void doReple(int input) {
		Article article = articleService.getArticle(input);
		if(article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.repleWrite(input);
		
		System.out.println(i +"번 게시글에 댓글을 달았습니다.");
		
	}

	private void doSelectBoard(int input) {
		Board board = articleService.selectBoard(input);
		if(board == null) {
			System.out.println("존재하지 않는 게시판입니다.");
			return;
		}
		System.out.println(board.boardName + " 게시판이 선택되었습니다.");
		Container.session.boardIdSave = board.boardId;
	}

	private void doMakeBoard() {
		Scanner scan = Container.scanner;
		
		System.out.println("게시판 이름: ");
		String boardName = scan.nextLine();
		
		int i = articleService.boardMake(boardName);
		
		System.out.println(i + "번째 게시판이 생성되었습니다.");
		
	}

	private void doWrite() {
		
		if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}else if(!Container.session.isBoardId()) {
			System.out.println("게시판을 선택 후 이용해주세요");
			return;
		}
		Scanner scan = Container.scanner;
		
		String title;
		String body;
		System.out.printf("제목: ");
		title = scan.nextLine(); 
		
		System.out.printf("내용:");
		body = scan.nextLine();
		
		int i = articleService.articleWrite(title,body,Container.session.loginIdSave,Container.session.boardIdSave);
		
		System.out.println(i + "번째 게시물 생성");
		
	}

	private void showDetail(int input) {

	/*	if(!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}*/
		
		Article article = articleService.getArticle(input);

		if (article == null) {
			System.out.println("존재하지 않는 게시물");
			return;
		}
		/*else if(article.memberId != Container.session.loginIdSave) {
			System.out.println("게시물 수정은 작성자만 할 수 있습니다.");
			return;
		}*/
		
		System.out.println("번호: " + article.id);
		System.out.println("날짜: " + article.regDate);
		System.out.println("수정일: " + article.updateDate);
		System.out.println("제목: " + article.title);
		System.out.println("내용: " + article.body);
		System.out.println("작성자: " + article.memberId);
		Board board = articleService.selectBoard(article.boardId);
		System.out.println("게시판: " + board.boardName);
		System.out.println("댓글: " + article.reple);
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
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목 / 게시판");
		
		List<Article> articles = articleService.getArticles();
		List<Article> boardAarticles = articleService.boardArticleList(Container.session.boardIdSave);
		if(boardAarticles == null) {
			System.out.println("게시판을 바르게 설정해주세요.");
			return;
		}else if(articles == null) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		for (Article article : boardAarticles) {
			Member member = Container.memberService.getLoginId(article.memberId);
			Board board = articleService.selectBoard(article.boardId);
			if(member != null) {
			System.out.printf("%d / %s / %s / %s / %s / %s \n", article.id, article.regDate, article.updateDate,
					member.name, article.title,board.boardName);
			}else System.out.println("멤버 못가져옴");
		}
	}

}
