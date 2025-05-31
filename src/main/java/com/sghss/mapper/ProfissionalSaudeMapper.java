package com.sghss.mapper;

import com.sghss.dto.ProfissionalSaudeDTO;
import com.sghss.model.ProfissionalSaude;

public class ProfissionalSaudeMapper {

	public static ProfissionalSaudeDTO toDTO(ProfissionalSaude entity) {
		ProfissionalSaudeDTO dto = new ProfissionalSaudeDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setCargo(entity.getCargo());
		dto.setEmail(entity.getEmail());
		dto.setTelefone(entity.getTelefone());
		dto.setEspecialidade(entity.getEspecialidade());
		return dto;
	}

	public static ProfissionalSaude toEntity(ProfissionalSaudeDTO dto) {
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
