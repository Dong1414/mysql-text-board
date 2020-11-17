package com.sbs.example.mysqlTextBoard.dao;

import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class MemberDao {

	public int join(String loginId, String loginPw, String name) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member` SET loginId = ?, loginPw = ?, `name` = ?, regDate = NOW(), updateDate = NOW()",
				loginId, loginPw, name);

		return MysqlUtil.insert(sql);

	}

	public Member login(String loginId) {
		Member member = null;

		SecSql sql = new SecSql();
		sql.append("select * from `member` where loginId = ?", loginId);

		Map<String, Object> memberlogin = MysqlUtil.selectRow(sql);

		if (memberlogin.isEmpty()) {
			return member;
		}
		member = new Member(memberlogin);
		return member;
	}

	public Member getLoginId(int memberId) {
		Member member = null;

		Map<String, Object> memberlogin = MysqlUtil
				.selectRow(new SecSql().append("select * from `member` where id = ?", memberId));

		if (memberlogin.isEmpty()) {
			return null;
		}

		return member = new Member(memberlogin);
	}

}
