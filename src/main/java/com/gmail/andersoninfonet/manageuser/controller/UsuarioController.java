package com.gmail.andersoninfonet.manageuser.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.andersoninfonet.manageuser.dto.UsuarioDTO;
import com.gmail.andersoninfonet.manageuser.security.JwtUser;
import com.gmail.andersoninfonet.manageuser.service.UsuarioService;
import com.gmail.andersoninfonet.manageuser.util.Response;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public @ResponseBody ResponseEntity<Response<List<UsuarioDTO>>> getUsuarios() {
		log.info("[ getUsuarios() ] - Listagem de usuario iniciada ...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var listDto = usuarioService.getListUsuario();
			if(listDto.isEmpty()) {
				response.getErrors().add("Nenhum Registro Encontrado.");
				log.error("[ getUsuarios() ] :( Nenhum Registro Encontrado.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(listDto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuarios() ] :( "+ ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuarios() ] :) Listagem de usuario finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','COMUM')")
	public ResponseEntity<Response<UsuarioDTO>> getUsuario(@PathVariable("id") String id, Authentication auth) {
		log.info("[ getUsuario(id) ] - Consulta de usuario iniciada...");
		var response = new Response<UsuarioDTO>();
		JwtUser user =  (JwtUser) auth.getPrincipal();
		
		try {			
			var dto = usuarioService.getUsuario(Long.valueOf(id));
 
			if(dto == null) {
				response.getErrors().add("Usuario não encontrado para o id "+id);
				log.error("[ getUsuario(id) ] :( Usuario não encontrado para o id "+id);
				return ResponseEntity.badRequest().body(response);
			} else if(!user.getId().equals(dto.getId()) && user.getRole().equals("ROLE_COMUM")) {
				response.getErrors().add("Desculpe , mas você só possui permissão para acessar seus dados.");
				log.error("[ getUsuario(id) ] :( Desculpe , mas você só possui permissão para acessar seus dados.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			log.error("[ getUsuario(id) ] :( "+ex.getMessage());
			response.getErrors().add(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuario(id) ] :) Consulta de usuario finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/nome/{nome}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<List<UsuarioDTO>>> getUsuarioByNome(@PathVariable("nome") String nome) {
		log.info("[ getUsuario(nome) ] - Consulta de usuario iniciada...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var dto = usuarioService.getUsuarioByNome(nome);
			if(dto.isEmpty()) {
				response.getErrors().add("Usuario não encontrado para o nome "+nome);
				log.error("[ getUsuario(nome) ] :( Usuario não encontrado para o nome "+nome);
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuario(nome) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuario(nome) ] :) Consulta de usuario finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/cpf/{cpf}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','COMUM')")
	public ResponseEntity<Response<UsuarioDTO>> getUsuarioByCPF(@PathVariable("cpf") String cpf, Authentication auth) {
		log.info("[ getUsuario(cpf) ] - Consulta de usuario iniciada...");
		var response = new Response<UsuarioDTO>();
		JwtUser user =  (JwtUser) auth.getPrincipal();
		try {
			var dto = usuarioService.getUsuarioByCPF(cpf);
			if(dto == null) {
				response.getErrors().add("Usuario não encontrado para o cpf "+cpf);
				log.error("[ getUsuario(cpf) ] :( Usuario não encontrado para o cpf "+cpf);
				return ResponseEntity.badRequest().body(response);
			}else if(!user.getCpf().equals(dto.getCpf()) && user.getRole().equals("ROLE_COMUM")) {
				response.getErrors().add("Desculpe , mas você só possui permissão para acessar seus dados.");
				log.error("[ getUsuario(cpf) ] :( Desculpe , mas você só possui permissão para acessar seus dados.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuario(cpf) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuario(cpf) ] - Consulta de usuario finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/cargo/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<List<UsuarioDTO>>> getUsuarioByCargo(@PathVariable("id") String id) {
		log.info("[ getUsuarioByCargo(id) ] - Listagem de usuario por cargo iniciada ...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var dto = usuarioService.getUsuarioByCargo(Long.valueOf(id));
			if(dto.isEmpty()) {
				response.getErrors().add("Usuarios não encontrados para o Cargo de id "+id);
				log.error("[ getUsuarioByCargo(id) ] :( Usuarios não encontrados para o Cargo de id "+id);
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuarioByCargo(id) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuarioByCargo(id) ] :) Listagem de usuario por cargo finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/perfil/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<List<UsuarioDTO>>> getUsuarioByPerfil(@PathVariable("id") String id) {
		log.info("[ getUsuarioByPerfil(id) ] - Listagem de usuario por perfil iniciada ...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var dto = usuarioService.getUsuarioByPerfil(Long.valueOf(id));
			if(dto.isEmpty()) {
				response.getErrors().add("Usuario não encontrado para o Perfil de id "+id);
				log.error("[ getUsuarioByPerfil(id) ] :( Usuario não encontrado para o Perfil de id "+id);
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuarioByPerfil(id) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuarioByPerfil(id) ] :) Listagem de usuario por perfil finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/status/{status}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<List<UsuarioDTO>>> getUsuarioByStatus(@PathVariable("status") String status) {
		log.info("[ getUsuarioByStatus(status) ] - Listagem de usuario por status iniciada ...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var dto = usuarioService.getUsuarioByStatus(Boolean.valueOf(status));
			if(dto.isEmpty()) {
				response.getErrors().add("Usuario não encontrado para o status "+status);
				log.error("[ getUsuarioByStatus(status) ] :( Usuario não encontrado para o status "+status);
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuarioByStatus(status) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuarioByStatus(status) ] :) Listagem de usuario por status finalizada.");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<UsuarioDTO>> addUsuario(@Valid @RequestBody UsuarioDTO dto, BindingResult result) {
		log.info("[ addUsuario(usuarioDTO) ] - Tentativa de adicionar um novo usuario iniciada ...");
		var response = new Response<UsuarioDTO>();
		try {
			usuarioService.validarDadosExistentes(dto, result);
			if(result.hasErrors()) {
				for(ObjectError erro : result.getAllErrors()) {
					response.getErrors().add(erro.getDefaultMessage());
				}
				log.error("[ addUsuario(usuarioDTO) ] :( Usuario não passou na validação.");
				return ResponseEntity.badRequest().body(response);
			}
			usuarioService.addUsuario(dto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ addUsuario(usuarioDTO) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ addUsuario(usuarioDTO) ] :) Usuario incluido com sucesso.");
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<UsuarioDTO>> deleteUsuario(@PathVariable("id") String id) {
		log.info("[ deleteUsuario(id) ] - Tentativa de excluir um usuario iniciada ...");
		var response = new Response<UsuarioDTO>();
		try {
			usuarioService.deleteUsuario(Long.valueOf(id));
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.info("[ deleteUsuario(id) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ deleteUsuario(id) ] :) Usuario excluido com sucesso.");
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<UsuarioDTO>> updateUsuario(@Valid @RequestBody UsuarioDTO dto, BindingResult result) {
		log.info("[ updateUsuario(usuarioDTO) ] - Tentativa de atualizar um usuario iniciada ...");
		var response = new Response<UsuarioDTO>();
		try {
			if(result.hasErrors()) {
				for(ObjectError erro : result.getAllErrors()) {
					response.getErrors().add(erro.getDefaultMessage());
				}
				log.error("[ updateUsuario(usuarioDTO) ] :( Usuario não passou na validação.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(usuarioService.updateUsuario(dto));
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ updateUsuario(usuarioDTO) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ updateUsuario(usuarioDTO) ] :) Usuario atualizado com sucesso.");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/inativar/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Response<UsuarioDTO>> inativarUsuario(@PathVariable("id") String id) {
		log.info("[ inativarUsuario(id) ] - Tentativa de inativar um usuario iniciada ...");
		var response = new Response<UsuarioDTO>();
		try {
			usuarioService.inativarUsuario(Long.valueOf(id));
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ inativarUsuario(id) ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	
		log.info("[ inativarUsuario(id) ] :) Usuario inativado com sucesso.");
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/feminino/maior", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public @ResponseBody ResponseEntity<Response<List<UsuarioDTO>>> getUsuariosFemininosMaiores() {
		log.info("[ getUsuariosFemininosMaiores() ] - Listagem de mulheres maiores de idade iniciada ...");
		var response = new Response<List<UsuarioDTO>>();
		try {
			var listDto = usuarioService.getListUsuarioFemininoMaiorDeIdade();
			if(listDto.isEmpty()) {
				response.getErrors().add("Nenhum Registro Encontrado.");
				log.error("[ getUsuariosFemininosMaiores() ] :( Nenhum Registro Encontrado.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(listDto);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getUsuariosFemininosMaiores() ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getUsuariosFemininosMaiores() ] :) Listagem de mulheres maiores de finalizada.");
		return ResponseEntity.ok(response);
	}
	@RequestMapping(value = "/cpf/0", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public @ResponseBody ResponseEntity<Response<List<String>>> getCPFStartBy0() {
		log.info("[ getCPFStartBy0() ] - Listagem de CPF's que começam com 0 iniciada ...");
		var response = new Response<List<String>>();
		try {
			var listCPF = usuarioService.getCPFStartBy0();
			if(listCPF.isEmpty()) {
				response.getErrors().add("Nenhum Registro Encontrado.");
				log.error("[ getCPFStartBy0() ] :( Nenhum Registro Encontrado.");
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(listCPF);
		} catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			log.error("[ getCPFStartBy0() ] :( "+ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		log.info("[ getCPFStartBy0() ] :) Listagem de CPF's que começam com 0 finalizada.");
		return ResponseEntity.ok(response);
	}
}
