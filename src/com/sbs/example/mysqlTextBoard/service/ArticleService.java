package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.ReCommand;
import com.sbs.example.mysqlTextBoard.dto.Reple;

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

	public int articleWrite(String title, String body, int loginId, int boardId) {
		
		return articleDao.save(title,body,loginId,boardId);
	}

	public int boardMake(String boardName) {
		return articleDao.boardMake(boardName);
		
	}

	public Board selectBoard(int input) {
		return articleDao.selectBoard(input);
		
	}

	public List<Article> getForPrintArticles(int boardId) {
		return articleDao.getForPrintArticles(boardId);
		
	}

	public int repleWrite(int input) {
		return articleDao.repleSave(input);
		
	}

	public List<Reple> getReple(int id) {
		return articleDao.getReple(id);
		
	}

	public List<Article> getList() {
		return articleDao.getList();		
	}

	public int repleModify(int input) {
		return articleDao.repleModify(input);
	}

	public int repleDelete(int input) {
		return articleDao.repleDelete(input);
		
	}

	public int reCommand(int input, int id) {
		return articleDao.reCommand(input,id);
	}

	public int getGood(int id) {
		return articleDao.getReGoods(id);
	}

	public int cancleReCommand(int input, int id) {
		return articleDao.cancelReCommand(input,id);		
	}

}
