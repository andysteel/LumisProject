package com.gmail.andersoninfonet.manageuser.repository.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.gmail.andersoninfonet.manageuser.config.DataSourceJDBCConfig;
import com.gmail.andersoninfonet.manageuser.model.Cargo;
import com.gmail.andersoninfonet.manageuser.model.Perfil;
import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.model.enums.Sexo;
import com.gmail.andersoninfonet.manageuser.repository.dao.UsuarioDAO;


@SpringBootTest
@ContextHierarchy(value = @ContextConfiguration(classes = DataSourceJDBCConfig.class))
public class UsuarioDAOTest {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Test
	public void deveListarTodosUsuariosDaBase() {
		var usuarios = usuarioDAO.getAllUsuarios();
		assertNotNull(usuarios);
	}
	
	@Test
	public void deveRecuperarUsuarioEspecificoDaBase() {
		var usuario = usuarioDAO.getUsuario(1L);
		assertNotNull(usuario);
	}
	
	@Test
	public void deveAtualizarUsuarioEspecificoDaBase() {
		var usuario = usuarioDAO.getUsuarioForOperations(1L);
		usuario.setAtivo(false);
		var update = usuarioDAO.updateUsuario(usuario);
		assertNotNull(update);
	}
	
	@Test
	public void deveInserirUmUsuario() {
		var usuario = new Usuario();
		var cargo = new Cargo();
		var encoder = new BCryptPasswordEncoder();
		cargo.setId(2L);
		var perfil = new Perfil();
		perfil.setId(2L);
		usuario.setNome("Eduardo Neri");
		usuario.setCpf("");
		usuario.setSexo(Sexo.MASCULINO);
		usuario.setDataNascimento(LocalDate.of(1986, 7, 15));
		usuario.setAtivo(true);
		usuario.setLogin("eduardo.neri");
		usuario.setSenha(encoder.encode("123456"));
		usuario.setCargo(cargo);
		usuario.setPerfil(perfil);
		var insert = usuarioDAO.addUsuario(usuario);
		assertEquals(1, insert);
	}
	
	@Test
	public void deveExcluirUsuarioEspecificoDaBase() {
		var usuario = usuarioDAO.getUsuarioForOperations(1L);
		var delete = usuarioDAO.deleteUsuario(usuario.getId());
		assertEquals(1, delete);
	}
}
