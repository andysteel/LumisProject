package com.gmail.andersoninfonet.manageuser.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.gmail.andersoninfonet.manageuser.model.enums.Sexo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8570280091390612167L;
	
	private Long id;
	private String nome;
	private String cpf;
	private Sexo sexo;
	private LocalDate dataNascimento;
	private boolean isAtivo;
	private String login;
	private String senha;
	private Cargo cargo;
	private Perfil perfil;
	
}
