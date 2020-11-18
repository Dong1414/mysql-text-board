package com.sbs.example.mysqlTextBoard.dto;

import java.util.List;
import java.util.Map;

public class Reple {
	
	public Reple(Map<String, Object> reple) {
		this.num = (int)reple.get("num");
		this.id = (int)reple.get("id");
		this.body = (String)reple.get("body");
	}
	public int num;
	public int id;
	public String body;
	
	

}
