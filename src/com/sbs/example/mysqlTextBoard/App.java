package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlutil.MysqlUtil;

public class App {
	public void run() {
		Scanner sc = Container.scanner;
		
		ArticleController articleController = new ArticleController();
		MemberController memberController = new MemberController();
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();
			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "mysqlutildemo");
			MysqlUtil.setDevMode(true);
			
			if (cmd.startsWith("article ")) {
				articleController.doCommand(cmd);
				MysqlUtil.closeConnection();
			}else if(cmd.startsWith("member ")) {
				memberController.doCommand(cmd);
				MysqlUtil.closeConnection();
			}else if (cmd.equals("system exit")) {
				System.out.println("== 시스템 종료 ==");
				MysqlUtil.closeConnection();
				break;
			}
		}
	}
}
