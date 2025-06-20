package com.sghss.mapper;

import org.springframework.stereotype.Component;

import com.sghss.dto.PacienteDTO;
import com.sghss.model.Paciente;

@Component
public class PacienteMapper {

	public Paciente toEntity(PacienteDTO dto) {
		Paciente paciente = new Paciente();
		paciente.setId(dto.getId());
		paciente.setNome(dto.getNome());
		paciente.setCpf(dto.getCpf());
		paciente.setDataNascimento(dto.getDataNascimento());
		paciente.setTelefone(dto.getTelefone());
		paciente.setEmail(dto.getEmail());
		return paciente;
	}

	public PacienteDTO toDTO(Paciente paciente) {
		PacienteDTO dto = new PacienteDTO();
		dto.setId(paciente.getId());
		dto.setNome(paciente.getNome());
		dto.setCpf(paciente.getCpf());
		dto.setDataNascimento(paciente.getDataNascimento());
		dto.setTelefone(paciente.getTelefone());
		dto.setEmail(paciente.getEmail());
		return dto;
	}
}
