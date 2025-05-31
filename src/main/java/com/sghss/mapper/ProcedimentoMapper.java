package com.sghss.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.model.Paciente;
import com.sghss.model.Procedimento;
import com.sghss.repository.PacienteRepository;

@Component
public class ProcedimentoMapper {

	@Autowired
	private PacienteRepository pacienteRepository;

	public ProcedimentoDTO toDTO(Procedimento entidade) {
		ProcedimentoDTO dto = new ProcedimentoDTO();
		dto.setId(entidade.getId());
		dto.setPacienteId(entidade.getPaciente() != null ? entidade.getPaciente().getId() : null);
		dto.setDescricao(entidade.getDescricao());
		dto.setTipo(entidade.getTipo());
		dto.setCodigo(entidade.getCodigo());
		dto.setData(entidade.getData());
		dto.setProfissionalResponsavel(entidade.getProfissionalResponsavel());
		return dto;
	}

	public Procedimento toEntity(ProcedimentoDTO dto) {
		Procedimento procedimento = new Procedimento();
		procedimento.setId(dto.getId());
		procedimento.setDescricao(dto.getDescricao());
		procedimento.setTipo(dto.getTipo());
		procedimento.setCodigo(dto.getCodigo());
		procedimento.setData(dto.getData());
		procedimento.setProfissionalResponsavel(dto.getProfissionalResponsavel());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
			procedimento.setPaciente(paciente);
		}

		return procedimento;
	}
}
