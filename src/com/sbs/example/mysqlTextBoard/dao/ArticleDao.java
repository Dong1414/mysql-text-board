package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));

		for (Map<String, Object> articleMap : articleMapList) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate = (String) articleMap.get("regDate");
			article.updateDate = (String) articleMap.get("updateDate");
			article.title = (String) articleMap.get("title");
			article.body = (String) articleMap.get("body");
			article.memberId = (int) articleMap.get("memberId");

			articles.add(article);
		}
		return articles;
	}

	public void delete(int input) {

		MysqlUtil.delete(new SecSql().append("DELETE FROM article WHERE id = ?", input));
	}

	public void modify(int input) {

		Scanner scan = new Scanner(System.in);
		System.out.printf("새 제목: ");
		String title = scan.nextLine();
		System.out.printf("새 내용: ");
		String body = scan.nextLine();

		MysqlUtil.update(new SecSql().append(
				"UPDATE article SET title =? , `body` = ?, updateDate = NOW() WHERE id = ?", title, body, input));

	}

	public Article getArticle(int input) {

		Map<String, Object> articleDtail = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article where id = ?", input));

		if (articleDtail != null) {
			Article article = new Article();
			article.id = (int) articleDtail.get("id");
			article.regDate = (String) articleDtail.get("regDate");
			article.updateDate = (String) articleDtail.get("updateDate");
			article.title = (String) articleDtail.get("title");
			article.body = (String) articleDtail.get("body");
			article.memberId = (int) articleDtail.get("memberId");

			return article;

		}
		return null;

	}

	public int save(String title, String body, int loginIdSave) {

		MysqlUtil.insert(new SecSql().append(
				"INSERT INTO `article` SET title = ?, `body` = ?, memberId = ?, regDate = NOW(), updateDate = NOW(), hit = 1",
				title, body, loginIdSave));
	
		return 1;

	}
}
