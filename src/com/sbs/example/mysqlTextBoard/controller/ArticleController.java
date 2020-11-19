package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.dto.ReCommand;
import com.sbs.example.mysqlTextBoard.dto.Reple;
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
		} else if (cmd.equals("article makeboard")) {
			doMakeBoard();
		} else if (cmd.startsWith("article selectboard ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doSelectBoard(input);
		} else if (cmd.startsWith("article reple ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doReple(input);
		} else if (cmd.startsWith("article replemodify ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doRepleModify(input);
		} else if (cmd.startsWith("article repledelete ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doRepleDelete(input);
		} else if (cmd.startsWith("article recommand ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doReCommand(input);
		} else if (cmd.startsWith("article cancelrecommand ")) {
			int input = Integer.parseInt(cmd.split(" ")[2]);
			doCancleReCommand(input);
		}
	}

	private void doCancleReCommand(int input) {
		if (!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		Article article = articleService.getArticle(input);
		if (article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.cancleReCommand(input,Container.session.loginIdSave);
		if (i != 0) {
			System.out.println(input + "번 게시물 추천 취소");
		}
	}

	private void doReCommand(int input) {
		if (!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}

		Article article = articleService.getArticle(input);
		if (article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.reCommand(input, Container.session.loginIdSave);
		if (i != 0) {
			System.out.println("추천 완료");
		}
	}

	private void doRepleDelete(int input) {
		Article article = articleService.getArticle(input);
		if (article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.repleDelete(input);
		if (i != 0) {
			System.out.println(i + "번 게시글에 댓글을 삭제하였습니다.");
		}

	}

	private void doRepleModify(int input) {
		Article article = articleService.getArticle(input);
		if (article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.repleModify(input);
		if (i != 0) {
			System.out.println(i + "번 게시글에 댓글을 수정하였습니다.");
		}
	}

	private void doReple(int input) {
		Article article = articleService.getArticle(input);
		if (article == null) {
			System.out.println("존재하지 않는 게시글 입니다.");
			return;
		}
		int i = articleService.repleWrite(input);

		System.out.println(i + "번 게시글에 댓글을 달았습니다.");

	}

	private void doSelectBoard(int input) {
		Board board = articleService.selectBoard(input);
		if (board == null) {
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

		if (!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		} else if (!Container.session.isBoardId()) {
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

		int i = articleService.articleWrite(title, body, Container.session.loginIdSave, Container.session.boardIdSave);

		System.out.println(i + "번째 게시물 생성");

	}

	private void showDetail(int input) {

		/*
		 * if(!Container.session.isLoginId()) { System.out.println("로그인 후 이용해주세요");
		 * return; }
		 */

		Article article = articleService.getArticle(input);

		if (article == null) {
			System.out.println("존재하지 않는 게시물");
			return;
		}
		/*
		 * else if(article.memberId != Container.session.loginIdSave) {
		 * System.out.println("게시물 수정은 작성자만 할 수 있습니다."); return; }
		 */

		System.out.println("번호: " + article.id);
		System.out.println("날짜: " + article.regDate);
		System.out.println("수정일: " + article.updateDate);
		System.out.println("제목: " + article.title);
		System.out.println("내용: " + article.body);
		System.out.println("작성자: " + article.memberId);
		
		System.out.println("조회수: " + article.hit);
		Board board = articleService.selectBoard(article.boardId);
		System.out.println("게시판: " + board.boardName);
		int goods = articleService.getGood(article.id);
		System.out.println("추천수: " + goods);

		List<Reple> reples = articleService.getReple(article.id);
		if (reples == null) {
			System.out.println("댓글 없음");
		} else {
			System.out.printf("댓글: ");
			for (Reple reple : reples) {
				System.out.println("      " + reple.body);
			}
		}

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
		if (!Container.session.isLoginId()) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		Article article = articleService.getArticle(input);

		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		} else if (article.memberId != Container.session.loginIdSave) {
			System.out.println(Container.session.loginIdSave);

			System.out.println("게시물 수정은 작성자만 할 수 있습니다.");
			return;
		}

		articleService.delete(input);
		System.out.println(input + "번재 게시글 삭제");
	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목 / 게시판 / 조회수 / 추천수");
		List<Article> articles = articleService.getArticles(); // 사이즈 체크용

		List<Article> boardAarticles = articleService.getForPrintArticles(Container.session.boardIdSave);

		if (boardAarticles == null) {
			System.out.println("게시판을 바르게 설정해주세요.");
			return;
		} else if (articles == null) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		for (Article article : boardAarticles) {

			Board board = articleService.selectBoard(article.boardId);

			System.out.printf("%d / %s / %s / %s / %s / %s / %d\n", article.id, article.regDate, article.updateDate,
					article.extra_writer, article.title, board.boardName, article.hit);

		}
	}
}
