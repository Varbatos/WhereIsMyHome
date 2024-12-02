package com.ssafy.Member.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.Member.model.MemberDto;


@Mapper
public interface MemberMapper {

	MemberDto login(@Param("id") String userId,@Param("password") String password);
	void join(MemberDto user) throws SQLException;
}
