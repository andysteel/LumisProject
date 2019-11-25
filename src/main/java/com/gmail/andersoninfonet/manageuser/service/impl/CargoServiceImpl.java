package com.gmail.andersoninfonet.manageuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.andersoninfonet.manageuser.dto.CargoDTO;
import com.gmail.andersoninfonet.manageuser.model.Cargo;
import com.gmail.andersoninfonet.manageuser.repository.jpa.CargoRepository;
import com.gmail.andersoninfonet.manageuser.service.CargoService;

@Service
public class CargoServiceImpl implements CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void addCargo(CargoDTO dto) {
		try {
			cargoRepository.save(mapToModel(dto));
		} catch(RuntimeException ex) {
			throw new RuntimeException("Erro ao tentar incluir um novo Cargo.");
		}
		
	}

	private Cargo mapToModel(CargoDTO dto) {
		var cargo = new Cargo();
		cargo.setNome(dto.getNome());
		return cargo;
	}
}
