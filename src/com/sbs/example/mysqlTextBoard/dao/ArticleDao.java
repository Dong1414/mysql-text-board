package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Reple;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {

			articles.add(new Article(articleMap));

		}
		return articles;
	}

	public void delete(int input) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article WHERE id = ?", input);
		MysqlUtil.delete(sql);
	}

	public void modify(int input) {

		Scanner scan = new Scanner(System.in);
		System.out.printf("새 제목: ");
		String title = scan.nextLine();
		System.out.printf("새 내용: ");
		String body = scan.nextLine();

		SecSql sql = new SecSql();
		sql.append("UPDATE article SET title =? , `body` = ?, updateDate = NOW() WHERE id = ?", title, body, input);

		MysqlUtil.update(sql);

	}

	public Article getArticle(int input) {
		Article article = null;
		SecSql sql = new SecSql();
		SecSql sqlUp = new SecSql();
		sqlUp.append("update article set hit = hit+1 where id = ?",input);
		MysqlUtil.update(sqlUp);
		sql.append("SELECT * FROM article where id = ?", input);

		Map<String, Object> articleDtail = MysqlUtil.selectRow(sql);

		if (articleDtail.isEmpty()) {
			return null;
		}
		return article = new Article(articleDtail);

	}

	public int save(String title, String body, int loginId, int boardId) {
		SecSql sql = new SecSql();
		sql.append(
				"INSERT INTO `article` SET title = ?, `body` = ?, memberId = ?, regDate = NOW(), updateDate = NOW(), boardId = ?",
				title, body, loginId, boardId);

		return MysqlUtil.insert(sql);

	}

	public int boardMake(String boardName) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO board set boardName = ?", boardName);

		return MysqlUtil.insert(sql);
	}

	public Board selectBoard(int input) {
		Board board = null;
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board WHERE id = ?", input);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);
		if (boardMap.isEmpty()) {
			return null;
		}
		return board = new Board(boardMap);

	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append(
				"SELECT article.*, `member`.name AS extra_writer FROM article INNER JOIN `member` ON article.memberId = `member`.id where article.boardId = ? ORDER BY article.id DESC",
				boardId);
		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		if (articleMapList.isEmpty()) {
			return null;
		}
		for (Map<String, Object> articleMap : articleMapList) {

			articles.add(new Article(articleMap));
		}
		return articles;

	}

	public int repleSave(int input) {
		Scanner scan = Container.scanner;
		SecSql sql = new SecSql();

		System.out.printf("댓글: ");
		String body = scan.nextLine();
		sql.append("INSERT INTO reple set num = ?, `body` = ?", input, body);

		return MysqlUtil.update(sql);
	}

	public List<Reple> getReple(int id) {
		List<Reple> reples = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM reple WHERE num = ?", id);

		List<Map<String, Object>> repleMaps = MysqlUtil.selectRows(sql);

		if (repleMaps.isEmpty()) {
			return null;
		}
		for (Map<String, Object> reple : repleMaps) {
			reples.add(new Reple(reple));
		}

		return reples;
	}

	public List<Article> getList() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append(
				"SELECT article.*, `member`.name AS extra_writer FROM article INNER JOIN `member` ON article.memberId = `member`.id");

		List<Map<String, Object>> articleMaps = MysqlUtil.selectRows(sql);

		if (articleMaps.isEmpty()) {
			return null;
		}
		for (Map<String, Object> articleMap : articleMaps) {
			Article article = new Article(articleMap);

			articles.add(article);

		}
		return articles;
	}

	public int repleModify(int input) {
		Scanner scan = Container.scanner;
		SecSql sql = new SecSql();
		SecSql sql1 = new SecSql();

		System.out.printf("수정 할 댓글: ");
		String body = scan.nextLine();
		sql.append("select * from reple where num = ? and `body` = ?", input, body);
		List<Map<String, Object>> repleMaps = MysqlUtil.selectRows(sql);
		if (repleMaps.isEmpty()) {
			System.out.println("해당 댓글이 존재하지 않습니다.");
			return 0;
		}
		System.out.printf("바꿀 내용: ");
		String newbody = scan.nextLine();
		sql1.append("UPDATE reple SET `body` = ? WHERE `body` = ? AND num = ?", newbody, body, input);
		MysqlUtil.update(sql1);
		return input;

	}

	public int repleDelete(int input) {
		Scanner scan = Container.scanner;
		SecSql sql = new SecSql();
		SecSql sql1 = new SecSql();

		System.out.printf("삭제 할 댓글: ");
		String body = scan.nextLine();
		sql.append("select * from reple where num = ? and `body` = ?", input, body);
		List<Map<String, Object>> repleMaps = MysqlUtil.selectRows(sql);
		if (repleMaps.isEmpty()) {
			System.out.println("해당 댓글이 존재하지 않습니다.");
			return 0;
		}
		sql1.append("delete from reple WHERE `body` = ? AND num = ?", body, input);
		MysqlUtil.update(sql1);
		return input;
	}

	public int reCommand(int input, int memberId) {
		SecSql memberSelSql = new SecSql();

		SecSql reCommandSql = new SecSql();

		memberSelSql.append("select * from reCommand where articleId = ? and memberId = ?", input, memberId);
		Map<String, Object> i = MysqlUtil.selectRow(memberSelSql);

		if (!i.isEmpty()) {
			System.out.println("추천 횟수 부족");
			return 0;
		}
		reCommandSql.append("insert into reCommand set articleConut = 1, articleId = ?, memberId = ?", input, memberId);
		MysqlUtil.update(reCommandSql);		
		
		return 1;

	}

	public int getReGoods(int id) {

		SecSql sql = new SecSql();
		sql.append("select COUNT(articleConut) AS 'sum' from reCommand where articleId = ?", id);
		Map<String, Object> goodsMap = MysqlUtil.selectRow(sql);

		if (goodsMap.isEmpty()) {
			return 0;
		}
		int i = (int) goodsMap.get("sum");
		return i;
	}

	public int cancelReCommand(int input, int memberId) {
		SecSql sqlSel = new SecSql();
		SecSql sqlDel = new SecSql();
		
		sqlSel.append("select * from reCommand where articleId = ? and memberId = ?",input, memberId);
		Map<String, Object> goodsMap = MysqlUtil.selectRow(sqlSel);
		if (goodsMap.isEmpty()) {
			return 0;
		}		
		
		sqlDel.append("delete from reCommand where articleId = ? and memberId = ? ",input,memberId);
		MysqlUtil.delete(sqlDel);		
		return 1;
	}
}
