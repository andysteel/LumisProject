package com.gmail.andersoninfonet.manageuser.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gmail.andersoninfonet.manageuser.model.Perfil;

public class PerfilRowMapper implements RowMapper<Perfil> {

	@Override
	public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
		var perfil = new Perfil();
		perfil.setId(rs.getLong("id"));
		perfil.setNome(rs.getString("nome"));
		perfil.setRole(rs.getString("role"));
		return perfil;
	}

}
