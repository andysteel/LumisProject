package com.gmail.andersoninfonet.manageuser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 2303556242462461725L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", length = 250, nullable = false)
	private String nome;
	
	@Column(name = "role", length = 250, nullable = false)
	private String role;
}
