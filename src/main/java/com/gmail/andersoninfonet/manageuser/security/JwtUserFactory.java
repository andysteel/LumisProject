package com.gmail.andersoninfonet.manageuser.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gmail.andersoninfonet.manageuser.model.Perfil;
import com.gmail.andersoninfonet.manageuser.model.Usuario;

public class JwtUserFactory {

	private JwtUserFactory() {
		
	}
	
	/**
	* Converte e gera um JwtUser com base nos dados de um funcionário.
	*
	* @param funcionario
	* @return JwtUser
	*/
	public static JwtUser create(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getLogin(),
				usuario.getSenha (), usuario.getCpf(), usuario.getPerfil().getRole(), mapToGrantedAuthorities(usuario.getPerfil()));
	}
	
	/**
	* Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	*
	* @param perfilEnum
	* @return List<GrantedAuthority>
	*/
	private static List< GrantedAuthority > mapToGrantedAuthorities(Perfil perfil) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.getRole()));
		return authorities ;
	}
	
}
