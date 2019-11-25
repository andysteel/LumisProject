package com.gmail.andersoninfonet.manageuser.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gmail.andersoninfonet.manageuser.model.Cargo;

public class CargoRowMapper implements RowMapper<Cargo> {

	@Override
	public Cargo mapRow(ResultSet rs, int rowNum) throws SQLException {
		var cargo = new Cargo();
		cargo.setId(rs.getLong("id"));
		cargo.setNome(rs.getString("nome"));
		return cargo;
	}

}
