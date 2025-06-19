package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.model.Paciente;
import com.sghss.model.Procedimento;
import com.sghss.model.ProfissionalSaude;

@Component
public class ProcedimentoMapper {

	public ProcedimentoDTO toDTO(Procedimento entidade) {
		ProcedimentoDTO dto = new ProcedimentoDTO();
		dto.setId(entidade.getId());
		dto.setDescricao(entidade.getDescricao());
		dto.setTipo(entidade.getTipo());
		dto.setCodigo(entidade.getCodigo());
		dto.setData(entidade.getData());

		if (entidade.getProfissional() != null) {
			dto.setProfissionalId(entidade.getProfissional().getId());
		}
		if (entidade.getPaciente() != null) {
			dto.setPacienteId(entidade.getPaciente().getId());
		}
		return dto;
	}

	public Procedimento toEntity(ProcedimentoDTO dto, Paciente paciente, ProfissionalSaude profissional) {
		Procedimento entidade = new Procedimento();
		entidade.setId(dto.getId());
		entidade.setDescricao(dto.getDescricao());
		entidade.setTipo(dto.getTipo());
		entidade.setCodigo(dto.getCodigo());
		entidade.setData(dto.getData());
		entidade.setPaciente(paciente);
		entidade.setProfissional(profissional);
		return entidade;
	}
}
