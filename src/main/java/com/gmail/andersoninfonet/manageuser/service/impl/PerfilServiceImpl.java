package com.gmail.andersoninfonet.manageuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.andersoninfonet.manageuser.dto.PerfilDTO;
import com.gmail.andersoninfonet.manageuser.model.Perfil;
import com.gmail.andersoninfonet.manageuser.repository.jpa.PerfilRepository;
import com.gmail.andersoninfonet.manageuser.service.PerfilService;

@Service
public class PerfilServiceImpl implements PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Override
	public void addPerfil(PerfilDTO dto) {
		try {
			perfilRepository.save(mapToModel(dto));
		} catch(RuntimeException ex) {
			throw new RuntimeException("Erro ao tentar incluir um novo Perfil.");
		}	
	}

	Perfil mapToModel(PerfilDTO dto) {
		var perfil = new Perfil();
		perfil.setNome(dto.getNome());
		perfil.setRole(dto.getRole());
		return perfil;
	}
}
