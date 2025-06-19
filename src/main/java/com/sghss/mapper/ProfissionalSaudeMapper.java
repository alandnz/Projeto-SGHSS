package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.ProfissionalSaudeDTO;
import com.sghss.model.ProfissionalSaude;

@Component
public class ProfissionalSaudeMapper {

	public ProfissionalSaudeDTO toDTO(ProfissionalSaude entity) {
		ProfissionalSaudeDTO dto = new ProfissionalSaudeDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setCargo(entity.getCargo());
		dto.setEmail(entity.getEmail());
		dto.setTelefone(entity.getTelefone());
		dto.setEspecialidade(entity.getEspecialidade());
		return dto;
	}

	public ProfissionalSaude toEntity(ProfissionalSaudeDTO dto) {
		ProfissionalSaude entity = new ProfissionalSaude();
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());
		entity.setCargo(dto.getCargo());
		entity.setEmail(dto.getEmail());
		entity.setTelefone(dto.getTelefone());
		entity.setEspecialidade(dto.getEspecialidade());
		return entity;
	}
}
