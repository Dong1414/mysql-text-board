package com.sbs.example.mysqlTextBoard;

public class Session {
	public int loginIdSave;
	
	
	
	public Session() {
		loginIdSave = 0;
	}
	
	public boolean isLoginId() {
		return loginIdSave != 0;
	}
}
