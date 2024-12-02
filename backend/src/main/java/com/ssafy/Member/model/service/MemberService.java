package com.ssafy.Member.model.service;

import java.sql.SQLException;

import com.ssafy.Member.model.MemberDto;


public interface MemberService {

	MemberDto login(String userId, String password);
	void join(MemberDto user) throws SQLException;

	
}
