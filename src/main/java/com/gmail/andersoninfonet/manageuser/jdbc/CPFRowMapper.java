package com.gmail.andersoninfonet.manageuser.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CPFRowMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		var cpf = new String();
		cpf = rs.getString("usr_cpf");
		return cpf;
	}

}
