package com.gmail.andersoninfonet.manageuser.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.security.JwtUserFactory;
import com.gmail.andersoninfonet.manageuser.service.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		Usuario usuario = usuarioService.getUsuarioByLogin(username);
		if (usuario != null && usuario.isAtivo()) {
			return JwtUserFactory.create(usuario);
		}
	
		throw new UsernameNotFoundException("Login não encontrado.");
	}

}
