package com.gmail.andersoninfonet.manageuser.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.gmail.andersoninfonet.manageuser.dto.UsuarioDTO;
import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.security.JwtUser;

public interface UsuarioService {

	UsuarioDTO getUsuario(Long id);
	
	List<UsuarioDTO> getUsuarioByNome(String nome);
	
	UsuarioDTO getUsuarioByCPF(String cpf);
	
	Usuario getUsuarioByLogin(String login);
	
	UsuarioDTO getUsuarioBySpecificNome(String nome);
	
	List<UsuarioDTO> getUsuarioByCargo(Long idCargo);
	
	List<UsuarioDTO> getUsuarioByPerfil(Long idPerfil);
	
	List<UsuarioDTO> getUsuarioByStatus(boolean status);
	
	List<UsuarioDTO> getListUsuario();
	
	List<UsuarioDTO> getListUsuarioFemininoMaiorDeIdade();
	
	List<String> getCPFStartBy0();
	
	void addUsuario(UsuarioDTO dto);
	
	UsuarioDTO updateUsuario(UsuarioDTO dto);
	
	void deleteUsuario(Long id);
	
	void validarDadosExistentes(UsuarioDTO dto, BindingResult result);
	
	void inativarUsuario(Long id);
	
	List<String> getAuthrities(JwtUser user);
}
