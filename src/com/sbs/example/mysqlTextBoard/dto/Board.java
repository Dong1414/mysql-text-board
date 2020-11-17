package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Board {
	public Board(Map<String, Object> boardMap) {
		this.boardId = (int) boardMap.get("id");
		this.boardName = (String) boardMap.get("boardName");
	}

	public int boardId;
	public String boardName;
}
