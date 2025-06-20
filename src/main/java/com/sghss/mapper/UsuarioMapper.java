package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.UsuarioDTO;
import com.sghss.model.Paciente;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.Usuario;

@Component
public class UsuarioMapper {

	public Usuario toEntity(UsuarioDTO dto, Paciente paciente, ProfissionalSaude profissional) {
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
		usuario.setPerfis(dto.getPerfis());
		usuario.setPaciente(paciente);
		usuario.setProfissional(profissional);
		return usuario;
	}

	public UsuarioDTO toDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());
		dto.setSenha(usuario.getSenha());
		dto.setPerfis(usuario.getPerfis());

		if (usuario.getPaciente() != null) {
			dto.setPacienteId(usuario.getPaciente().getId());
		}

		if (usuario.getProfissional() != null) {
			dto.setProfissionalId(usuario.getProfissional().getId());
		}

		return dto;
	}
}
