package com.gmail.andersoninfonet.manageuser.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gmail.andersoninfonet.manageuser.model.Perfil;

import lombok.Data;

@Data
public class PerfilDTO {

	private Long id;
	
	@NotEmpty(message = "Nome do Perfil não pode estar vazio.")
	@NotBlank(message = "Nome do Perfil não pode estar em branco.")
	@NotNull(message = "Nome do Perfil não pode ser nulo.")
	@Size(max = 250, message = "O nome do Perfil deve conter até 250 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Nome da Regra de Segurança não pode estar vazio.")
	@NotBlank(message = "Nome da Regra de Segurança não pode estar em branco.")
	@NotNull(message = "Nome da Regra de Segurança não pode ser nulo.")
	@Size(max = 250, message = "O nome da Regra de Segurança deve conter até 250 caracteres.")
	private String role;
	
	public PerfilDTO(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		this.role = perfil.getRole();
	}
	
	public PerfilDTO() {

	}
}
