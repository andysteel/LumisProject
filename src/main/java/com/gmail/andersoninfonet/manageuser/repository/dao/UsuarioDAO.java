package com.gmail.andersoninfonet.manageuser.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.gmail.andersoninfonet.manageuser.jdbc.CPFRowMapper;
import com.gmail.andersoninfonet.manageuser.jdbc.UsuarioRowMapper;
import com.gmail.andersoninfonet.manageuser.jdbc.UsuarioRowMapperForConsulta;
import com.gmail.andersoninfonet.manageuser.model.Usuario;

@Repository
public class UsuarioDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final int OPERACAO_COM_SUCESSO = 1;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("usuario");
    }
    
    public List<Usuario> getAllUsuarios() {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id");
        return jdbcTemplate.query(sqlBuffer.toString(), new UsuarioRowMapperForConsulta());
    }
    
    public List<Usuario> getAllUsuariosFemininos() {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ") 
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.sexo = 'FEMININO'");
        return jdbcTemplate.query(sqlBuffer.toString(), new UsuarioRowMapperForConsulta());
    }
    
    public int addUsuario(Usuario user) {
        var parameters = usuarioParameters(user);
        return simpleJdbcInsert.execute(parameters);
    }
    
    public Usuario getUsuario(Long id) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.id = ?");
        return jdbcTemplate.queryForObject(sqlBuffer.toString(), new Object[] { id }, new UsuarioRowMapperForConsulta());
    }
    
    public Usuario getUsuarioForOperations(Long id) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, usr.senha as usr_senha, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.id = ?");
        return jdbcTemplate.queryForObject(sqlBuffer.toString(), new Object[] { id }, new UsuarioRowMapper());
    }
    
    public Usuario getUsuarioBySpecificNome(String nome) {
    	var sqlBuffer = new StringBuffer();

    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.nome = ?");
    	return jdbcTemplate.queryForObject(sqlBuffer.toString(), new Object[] { nome }, new UsuarioRowMapperForConsulta());
    }
    
    public List<Usuario> getUsuarioByNome(String nome) {
    	var sqlBuffer = new StringBuffer();
    	var mapSource = new HashMap<String, Object>();
    	mapSource.put("nome", "%"+nome+"%");
    	var map = new MapSqlParameterSource(mapSource);
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.nome LIKE :nome");
    	return namedParameterJdbcTemplate.query(sqlBuffer.toString(), map, new UsuarioRowMapperForConsulta());
    }
    
    public List<String> getCPFStartBy0() {
    	var sqlBuffer = new StringBuffer();
    	var mapSource = new HashMap<String, Object>();
    	mapSource.put("cpf", "0%");
    	var map = new MapSqlParameterSource(mapSource);
    	sqlBuffer.append("SELECT usr.cpf as usr_cpf ")
    				.append("FROM usuario usr ")
    				.append("WHERE usr.cpf LIKE :cpf");
    	return namedParameterJdbcTemplate.query(sqlBuffer.toString(), map, new CPFRowMapper());
    }
    
    public Usuario getUsuarioByCPF(String cpf) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.cpf = ?");
        return jdbcTemplate.queryForObject(sqlBuffer.toString(), new Object[] { cpf }, new UsuarioRowMapperForConsulta());
    }
    
    public Usuario getUsuarioByLogin(String login) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, usr.senha as usr_senha, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.login = ?");
        return jdbcTemplate.queryForObject(sqlBuffer.toString(), new Object[] { login }, new UsuarioRowMapper());
    }
    
    public List<Usuario> getUsuarioByCargo(Long id) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ") 
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE cg.id = ?");
        return jdbcTemplate.query(sqlBuffer.toString(), new Object[] { id }, new UsuarioRowMapperForConsulta());
    }
    
    public List<Usuario> getUsuarioByPerfil(Long id) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE pf.id = ?");
        return jdbcTemplate.query(sqlBuffer.toString(), new Object[] { id }, new UsuarioRowMapperForConsulta());
    }
    
    public List<Usuario> getUsuarioByStatus(boolean ativo) {
    	var sqlBuffer = new StringBuffer();
    	sqlBuffer.append("SELECT usr.id as usr_id, usr.nome as usr_nome, usr.cpf as usr_cpf, usr.sexo as usr_sexo, ")
    			 	.append("usr.data_nascimento as usr_data_nascimento, usr.ativo as usr_ativo, ")
    			 	.append("usr.login as usr_login, ")
    				.append("cg.id as cg_id, cg.nome as cg_nome, pf.id as pf_id, ")
    				.append("pf.nome as pf_nome, pf.role as pf_role FROM usuario usr INNER JOIN cargo cg ")
    				.append("on usr.id_cargo = cg.id INNER JOIN perfil pf ")
    				.append("on usr.id_perfil = pf.id WHERE usr.ativo = ?");
        return jdbcTemplate.query(sqlBuffer.toString(), new Object[] { ativo }, new UsuarioRowMapperForConsulta());
    }
    
    public Usuario updateUsuario(Usuario usuario) {
    	var map = new MapSqlParameterSource(usuarioParameters(usuario));
        var query = "UPDATE usuario set nome=:nome, cpf=:cpf, sexo=:sexo, data_nascimento=data_nascimento, ativo=:ativo, login=:login, senha=:senha, id_cargo=:id_cargo, id_perfil=:id_perfil where id=:id";
        var update = namedParameterJdbcTemplate.update(query, map);
        Usuario user = null;
        if(update == OPERACAO_COM_SUCESSO) {
        	user = getUsuario(usuario.getId());
        }
        return user;
    }
    
    public int deleteUsuario(Long id) {
    	var sql = "DELETE from usuario where id = ?";
    	Object[] args = new Object[] {id};
    	return jdbcTemplate.update(sql, args);
    }
    
    public Map<String, Object> usuarioParameters(Usuario user) {

    	var dataSql = java.sql.Date.valueOf(user.getDataNascimento());
        final var parameters = new HashMap<String, Object>();
        parameters.put("id", user.getId());
        parameters.put("nome", user.getNome());
        parameters.put("cpf", user.getCpf());
        parameters.put("sexo", user.getSexo().ordinal());
        parameters.put("data_nascimento", dataSql);
        parameters.put("ativo", user.isAtivo());
        parameters.put("login", user.getLogin());
        parameters.put("senha", user.getSenha());
        parameters.put("id_cargo", user.getCargo().getId());
        parameters.put("id_perfil", user.getPerfil().getId());
        return parameters;
    }
}
