package com.sbs.example.mysqlTextBoard;

public class Session {
	public int loginIdSave;
	public int boardIdSave;
	
	
	public Session() {
		loginIdSave = 0;
		boardIdSave = 0;
	}
	
	public boolean isLoginId() {
		return loginIdSave != 0;
	}
	
	public boolean isBoardId() {
		return boardIdSave != 0;
	}
}
