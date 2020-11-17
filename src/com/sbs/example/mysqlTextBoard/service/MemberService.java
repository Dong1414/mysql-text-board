package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {

	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}

	public Member login(String loginId) {
		return memberDao.login(loginId);		
	}

	public void join(String loginId, String loginPw, String name) {
		memberDao.join(loginId, loginPw, name);
		
	}

	public Member getLoginId(int memberId) {
		
		return memberDao.getLoginId(memberId);
	}

}
