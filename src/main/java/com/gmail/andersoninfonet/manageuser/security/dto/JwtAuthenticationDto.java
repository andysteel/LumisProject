package com.gmail.andersoninfonet.manageuser.security.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

	@NotEmpty(message = "Login não pode ser vazio.")
	private String login;
	
	@NotEmpty(message = "Senha não pode ser vazia.")
	private String senha;

	public JwtAuthenticationDto() {

	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [login=" + login + ", senha=" + senha + "]";
	}
}
