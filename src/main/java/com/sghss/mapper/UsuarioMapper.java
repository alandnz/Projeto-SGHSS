package com.sghss.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sghss.dto.UsuarioDTO;
import com.sghss.model.Paciente;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.Usuario;
import com.sghss.repository.PacienteRepository;
import com.sghss.repository.ProfissionalSaudeRepository;

@Component
public class UsuarioMapper {

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private ProfissionalSaudeRepository profissionalRepository;

	public Usuario toEntity(UsuarioDTO dto) {
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
		usuario.setPerfis(dto.getPerfis());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
			usuario.setPaciente(paciente);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
			usuario.setProfissional(profissional);
		}

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
