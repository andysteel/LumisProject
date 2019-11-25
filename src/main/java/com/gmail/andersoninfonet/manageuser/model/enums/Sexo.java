package com.gmail.andersoninfonet.manageuser.model.enums;

public enum Sexo {

	MASCULINO("Masculino"),
	FEMININO("Feminino"),
	OUTROS("Outros");
	
	String descricao;
	
	Sexo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
