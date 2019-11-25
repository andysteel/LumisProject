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

import com.gmail.andersoninfonet.manageuser.dto.PerfilDTO;
import com.gmail.andersoninfonet.manageuser.service.PerfilService;
import com.gmail.andersoninfonet.manageuser.util.Response;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

	private static final Logger log = LoggerFactory.getLogger(PerfilController.class);
	
	@Autowired
	private PerfilService perfilService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<PerfilDTO>> addPerfil(@Valid @RequestBody PerfilDTO dto, BindingResult result) {
		log.info("[ addPerfil(perfilDTO) ] - Tentativa de adicionar um novo perfil iniciada ...");
		var response = new Response<PerfilDTO>();
		try {
			
			if(result.hasErrors()) {
				for(ObjectError erro : result.getAllErrors()) {
					response.getErrors().add(erro.getDefaultMessage());
				}
				log.error("[ addPerfil(perfilDTO) ] :( Perfil não passou na validação.");
				return ResponseEntity.badRequest().body(response);
			}
			perfilService.addPerfil(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ addPerfil(perfilDTO) ] :( "+ex.getMessage() +" "+ ex.getCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ addPerfil(perfilDTO) ] :) Perfil incluido com sucesso.");
		return ResponseEntity.ok().build();
	}
}
