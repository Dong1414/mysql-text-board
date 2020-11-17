package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public void delete(int input) {
		articleDao.delete(input);
		
	}

	public void modify(int input) {
		articleDao.modify(input);
		
	}

	public Article getArticle(int input) {
		return articleDao.getArticle(input);
		
	}

	public int articleWrite(String title, String body, int loginIdSave) {
		
		return articleDao.save(title,body,loginIdSave);
	}

}
