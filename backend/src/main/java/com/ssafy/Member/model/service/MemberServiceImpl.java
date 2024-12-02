package com.ssafy.Member.model.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.Member.model.MemberDto;
import com.ssafy.Member.model.mapper.MemberMapper;


@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper mapper;

	public MemberServiceImpl(MemberMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public MemberDto login(String userId, String password) {
		return mapper.login(userId, password);
	}

	@Override
	public void join(MemberDto user) throws SQLException {
		mapper.join(user);

	}
}
