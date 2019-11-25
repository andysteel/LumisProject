package com.gmail.andersoninfonet.manageuser.dto;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.model.enums.Sexo;

import lombok.Data;

@Data
public class UsuarioDTO {

	private Long id;
	
	@NotEmpty(message = "Nome do usuário não pode estar vazio.")
	@NotBlank(message = "Nome do usuário não pode estar em branco.")
	@NotNull(message = "Nome do usuário não pode ser nulo.")
	@Size(max = 250, message = "O nome do usuário deve conter até 250 caracteres.")
	private String nome;
	
	@CPF(message = "CPF inválido")
	@Digits(integer = 11, fraction = 0,  message = "CPF deve conter apenas números.")
	@Size(min = 11, max = 11, message = "CPF deve conter 11 digitos.")
	@NotEmpty(message = "CPF do usuário não pode esta vazio.")
	@NotBlank(message = "CPF do usuário não pode estar em branco.")
	@NotNull(message = "CPF do usuário não pode ser nulo.")
	private String cpf;
	
	@NotNull(message = "Sexo do usuário não pode ser nulo.")
	private Sexo sexo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data de nascimento do usuário não pode ser nulo.")
	private LocalDate dataNascimento;
	
	@NotNull(message = "Status de nascimento do usuário não pode ser nulo.")
	private boolean isAtivo;
	
	@NotEmpty(message = "Login do usuário não pode estar vazio.")
	@NotBlank(message = "Login do usuário não pode estar em branco.")
	@NotNull(message = "Login do usuário não pode ser nulo.")
	@Size(max = 100, message = "O login do usuário deve conter até 100 caracteres.")
	private String login;
	
	@NotEmpty(message = "A senha do usuário não pode estar vazia.")
	@NotBlank(message = "A senha do usuário não pode estar em branco.")
	@NotNull(message = "A senha do usuário não pode ser nula.")
	@Size(max = 250, message = "A senha do usuário deve conter até 250 caracteres.")
	private String senha;
	
	@NotNull(message = "Cargo do usuário não pode ser nulo.")
	private CargoDTO cargo;
	
	@NotNull(message = "Cargo do usuário não pode ser nulo.")
	private PerfilDTO perfil;
	
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.sexo = usuario.getSexo();
		this.dataNascimento = usuario.getDataNascimento();
		this.isAtivo = usuario.isAtivo();
		this.login = usuario.getLogin();
		if(usuario.getSenha() != null) {
			this.senha = usuario.getSenha();
		}	
		this.cargo = new CargoDTO(usuario.getCargo());
		this.perfil = new PerfilDTO(usuario.getPerfil());
	}
	
	public UsuarioDTO() {
		
	}
}
