package com.gmail.andersoninfonet.manageuser.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails, Serializable{

	private static final long serialVersionUID = -9102023878077137035L;
	
	private Long id;
	private String username;
	private String password;
	private String cpf;
	private String role;
	private Collection <? extends GrantedAuthority > authorities;
	
	public JwtUser(Long id, String username, String password,String cpf, 
			String role, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;		
		this.password = password;
		this.cpf = cpf;
		this.role = role;
		this.authorities = authorities;
	}

	public Long getId() {
		return id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getRole() {
		return role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
