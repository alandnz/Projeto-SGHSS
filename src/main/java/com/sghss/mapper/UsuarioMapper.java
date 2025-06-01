package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.UsuarioDTO;
import com.sghss.model.Usuario;

@Component
public class UsuarioMapper {

	public static UsuarioDTO toDTO(Usuario entidade) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setEmail(entidade.getEmail());
		dto.setPerfis(entidade.getPerfis());
		// Senha não é exposta normalmente
		return dto;
	}

	public static Usuario toEntity(UsuarioDTO dto) {
		Usuario entidade = new Usuario();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setEmail(dto.getEmail());
		entidade.setSenha(dto.getSenha());
		entidade.setPerfis(dto.getPerfis());
		return entidade;
	}
}
