package com.gmail.andersoninfonet.manageuser.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gmail.andersoninfonet.manageuser.model.Cargo;

import lombok.Data;

@Data
public class CargoDTO {

	private Long id;
	
	@NotEmpty(message = "Nome do Cargo não pode estar vazio.")
	@NotBlank(message = "Nome do Cargo não pode estar em branco.")
	@NotNull(message = "Nome do Cargo não pode ser nulo.")
	@Size(max = 250, message = "O nome do Cargo deve conter até 250 caracteres.")
	private String nome;
	
	public CargoDTO(Cargo cargo) {
		this.id = cargo.getId();
		this.nome = cargo.getNome();
	}
	
	public CargoDTO() {

	}
}
