package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.model.Paciente;
import com.sghss.model.Procedimento;
import com.sghss.model.ProfissionalSaude;
import com.sghss.repository.PacienteRepository;
import com.sghss.repository.ProfissionalSaudeRepository;

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

	public Procedimento toEntity(ProcedimentoDTO dto, PacienteRepository pacienteRepository,
			ProfissionalSaudeRepository profissionalRepository) {
		Procedimento entidade = new Procedimento();
		entidade.setId(dto.getId());
		entidade.setDescricao(dto.getDescricao());
		entidade.setTipo(dto.getTipo());
		entidade.setCodigo(dto.getCodigo());
		entidade.setData(dto.getData());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
			entidade.setPaciente(paciente);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
			entidade.setProfissional(profissional);
		}

		return entidade;
	}
}
