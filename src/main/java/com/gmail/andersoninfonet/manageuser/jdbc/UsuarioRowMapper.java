package com.gmail.andersoninfonet.manageuser.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gmail.andersoninfonet.manageuser.model.Cargo;
import com.gmail.andersoninfonet.manageuser.model.Perfil;
import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.model.enums.Sexo;

public class UsuarioRowMapper implements RowMapper<Usuario> {
	
	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		var usuario = new Usuario();
		var cargo = new Cargo();
		var perfil = new Perfil();
		
			usuario.setId(rs.getLong("usr_id"));
			usuario.setNome(rs.getString("usr_nome"));
			usuario.setCpf(rs.getString("usr_cpf"));
			usuario.setSexo(Sexo.valueOf(rs.getString("usr_sexo")));
			var dataTipoDate = new Date(rs.getDate("usr_data_nascimento").getTime()); 
			usuario.setDataNascimento(dataTipoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			usuario.setAtivo(rs.getBoolean("usr_ativo"));
			usuario.setLogin(rs.getString("usr_login"));
			usuario.setSenha(rs.getString("usr_senha"));
			cargo.setId(rs.getLong("cg_id"));
			cargo.setNome(rs.getString("cg_nome"));
			perfil.setId(rs.getLong("pf_id"));
			perfil.setNome(rs.getString("pf_nome"));
			perfil.setRole(rs.getString("pf_role"));
			
			usuario.setCargo(cargo);
			usuario.setPerfil(perfil);
			
		return usuario;
	}

}
