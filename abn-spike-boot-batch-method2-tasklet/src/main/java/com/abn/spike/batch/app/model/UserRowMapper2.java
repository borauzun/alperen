package com.abn.spike.batch.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper2 implements RowMapper<User2> {

	//@Override
	public User2 mapRow(ResultSet rs, int rowNum) throws SQLException {

		User2 user = new User2();

		user.setBC_NUMBER(rs.getInt("BC_NUMBER"));
		user.setEXTERNAL_UID(rs.getString("EXTERNAL_UID"));
		user.setPROCESS_ID(rs.getString("PROCESS_ID"));
		user.setPROCESS_STATUS(rs.getString("PROCESS_STATUS"));
		user.setIBAN(rs.getString("IBAN"));
		user.setTHIRD_PARTY_URL(rs.getString("THIRD_PARTY_URL"));
		
		
        System.out.println("---:"+user.getBC_NUMBER());
		return user;
	}

}