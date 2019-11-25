package com.gmail.andersoninfonet.manageuser.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.gmail.andersoninfonet.manageuser.dto.UsuarioDTO;
import com.gmail.andersoninfonet.manageuser.model.Cargo;
import com.gmail.andersoninfonet.manageuser.model.Perfil;
import com.gmail.andersoninfonet.manageuser.model.Usuario;
import com.gmail.andersoninfonet.manageuser.repository.dao.UsuarioDAO;
import com.gmail.andersoninfonet.manageuser.security.JwtUser;
import com.gmail.andersoninfonet.manageuser.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	private static final int OPERACAO_COM_SUCESSO = 1;
	private static final int MAIOR_IDADE = 18;
	
	@Override
	public UsuarioDTO getUsuario(Long id) {
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.getUsuario(id);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return mapToDTO(usuario);
	}
	
	@Override
	public UsuarioDTO getUsuarioBySpecificNome(String nome) {
		Usuario usuario = null;
		UsuarioDTO dto = null;
		try {
			usuario = usuarioDAO.getUsuarioBySpecificNome(nome);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if( ex instanceof EmptyResultDataAccessException) {
				return dto;
			}
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		if(usuario != null) {
			dto = mapToDTO(usuario);
		}
		return dto;
	}
	
	@Override
	public List<UsuarioDTO>  getUsuarioByNome(String nome) {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getUsuarioByNome(nome);
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.collect(Collectors.toList());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listDTO;
	}

	@Override
	public UsuarioDTO getUsuarioByCPF(String cpf) {
		Usuario usuario = null;
		UsuarioDTO dto = null;
		try {
			usuario = usuarioDAO.getUsuarioByCPF(cpf);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if( ex instanceof EmptyResultDataAccessException) {
				return dto;
			}
			throw new RuntimeException("Nenhum registro encontrado.");
		}
		if(usuario != null) {
			dto = mapToDTO(usuario);
		}
		return dto;
	}

	@Override
	public List<UsuarioDTO> getUsuarioByCargo(Long idCargo) {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getUsuarioByCargo(idCargo);
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.collect(Collectors.toList());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listDTO;
	}

	@Override
	public List<UsuarioDTO> getUsuarioByPerfil(Long idPerfil) {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getUsuarioByPerfil(idPerfil);
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.collect(Collectors.toList());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listDTO;
	}

	@Override
	public List<UsuarioDTO> getUsuarioByStatus(boolean status) {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getUsuarioByStatus(status);
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.collect(Collectors.toList());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listDTO;
	}

	@Override
	public List<UsuarioDTO> getListUsuario() {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getAllUsuarios();
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.collect(Collectors.toList());
		} catch(RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Houve um erro ao tentar listar usuarios");
		}
		return listDTO;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void addUsuario(UsuarioDTO dto) {
		try {
			usuarioDAO.addUsuario(mapToModel(dto));
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao tentar inserir um usuário");
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public UsuarioDTO updateUsuario(UsuarioDTO dto) {
		String msgErro = null;
		Usuario user = null;
		try {
			var usuario = mapToModel(dto);
			user = usuarioDAO.updateUsuario(usuario);
			if(user == null) {
				msgErro = "Usuário não encontrado para atualização.";
				throw new RuntimeException();
			}
		} catch(RuntimeException ex) {
			ex.printStackTrace();
			if(msgErro != null) {
				throw new RuntimeException(msgErro);
			}
			throw new RuntimeException("Erro ao tentar atualizar usuário.");
		}

		return mapToDTO(user);
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deleteUsuario(Long id) {
		String msgErro = null; 
		try {
			int delete = usuarioDAO.deleteUsuario(id);
			if(delete != OPERACAO_COM_SUCESSO) {
				msgErro = "Usuário não encontrado ou impossibilitado de ser Excluído";
				throw new RuntimeException();
			}
		} catch(RuntimeException ex) { 
			ex.printStackTrace();
			if(msgErro != null) {
				throw new RuntimeException(msgErro);
			}
			throw new RuntimeException("Erro ao tentar excluir usuário");
		}
	}
	
	private Usuario mapToModel(UsuarioDTO dto) {
		var usuario = new Usuario();
		var cargo = new Cargo();
		var perfil = new Perfil();
		
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setCpf(dto.getCpf());
		usuario.setSexo(dto.getSexo());
		usuario.setDataNascimento(dto.getDataNascimento());
		usuario.setAtivo(dto.isAtivo());
		usuario.setLogin(dto.getLogin());
		if(dto.getSenha() != null) {
			usuario.setSenha(dto.getSenha());
		}
		cargo.setId(dto.getCargo().getId());
		cargo.setNome(dto.getCargo().getNome());
		usuario.setCargo(cargo);
		perfil.setId(dto.getPerfil().getId());
		perfil.setNome(dto.getPerfil().getNome());
		perfil.setRole(dto.getPerfil().getRole());
		usuario.setPerfil(perfil);
		
		return usuario;
	}
	
	private UsuarioDTO mapToDTO(Usuario usuario) {
		var dto = new UsuarioDTO(usuario);
		return dto;
	}

	@Override
	public void validarDadosExistentes(UsuarioDTO dto, BindingResult result) {
		var usuarioDTOCPF = getUsuarioByCPF(dto.getCpf());
		if(usuarioDTOCPF != null) {
			result.addError(new ObjectError("usuarioDTOCPF", "Este CPF já existe na base de usuários."));
		}
		
		var usuarioDTONome = getUsuarioBySpecificNome(dto.getNome());
		if(usuarioDTONome != null) {
			result.addError(new ObjectError("usuarioDTONome", "Este nome já existe na base de usuários."));
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void inativarUsuario(Long id) {
		String msgErro = null;
		try {
			var usuario = usuarioDAO.getUsuarioForOperations(id);
			var usuarioDTO = mapToDTO(usuario);
			if(usuarioDTO.isAtivo()) {
				usuarioDTO.setAtivo(false);
				updateUsuario(usuarioDTO);
			} else {
				msgErro = "Usuário ja se encontra inativado.";
				throw new RuntimeException();
			}
		} catch(RuntimeException ex) {
			ex.printStackTrace();
			if(msgErro != null) {
				throw new RuntimeException(msgErro);
			} else {
				throw new RuntimeException("Erro ao inativar usuário.");
			}
			
		}
	
	}

	@Override
	public List<UsuarioDTO> getListUsuarioFemininoMaiorDeIdade() {
		List<UsuarioDTO> listDTO = null;
		try {
			var usuarios = usuarioDAO.getAllUsuariosFemininos();
			listDTO = usuarios.stream().map(UsuarioDTO :: new)
										.filter(usr -> calcularIdade(usr.getDataNascimento() , LocalDate.now()) > MAIOR_IDADE)
										.collect(Collectors.toList());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listDTO;
	}

	private int calcularIdade(LocalDate nascimento, LocalDate dataAtual) {
		return Period.between(nascimento, dataAtual).getYears();
	}

	@Override
	public List<String> getCPFStartBy0() {
		List<String> listCPF = null;
		try {			
			listCPF = usuarioDAO.getCPFStartBy0();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return listCPF;
	}

	@Override
	public Usuario getUsuarioByLogin(String login) {
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.getUsuarioByLogin(login);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Nenhum registro encontrado.");
		}		
		return usuario;
	}

	@Override
	public List<String> getAuthrities(JwtUser user) {
		var roles = user.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toList());
		return roles;
	}
}
