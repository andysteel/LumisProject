package com.gmail.andersoninfonet.manageuser.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.andersoninfonet.manageuser.dto.CargoDTO;
import com.gmail.andersoninfonet.manageuser.service.CargoService;
import com.gmail.andersoninfonet.manageuser.util.Response;

@RestController
@RequestMapping("/cargos")
public class CargoController {
	
	private static final Logger log = LoggerFactory.getLogger(CargoController.class);

	@Autowired
	private CargoService cargoService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<CargoDTO>> addCargo(@Valid @RequestBody CargoDTO dto, BindingResult result) {
		log.info("[ addCargo(cargoDTO) ] - Tentativa de adicionar um novo cargo iniciada ...");
		var response = new Response<CargoDTO>();
		try {
			
			if(result.hasErrors()) {
				for(ObjectError erro : result.getAllErrors()) {
					response.getErrors().add(erro.getDefaultMessage());
				}
				log.error("[ addCargo(cargoDTO) ] :( Cargo não passou na validação.");
				return ResponseEntity.badRequest().body(response);
			}
			cargoService.addCargo(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ addCargo(cargoDTO) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ addCargo(cargoDTO) ] :) Cargo inlcuido com sucesso.");
		return ResponseEntity.ok().build();
	}
}
