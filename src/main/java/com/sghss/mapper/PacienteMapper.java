package com.sghss.mapper;

import com.sghss.dto.PacienteDTO;
import com.sghss.model.Paciente;

public class PacienteMapper {

	public static Paciente toEntity(PacienteDTO dto) {
		Paciente paciente = new Paciente();
		paciente.setNome(dto.getNome());
		paciente.setCpf(dto.getCpf());
		paciente.setDataNascimento(dto.getDataNascimento());
		paciente.setTelefone(dto.getTelefone());
		paciente.setEmail(dto.getEmail());
		return paciente;
	}

	public static PacienteDTO toDTO(Paciente paciente) {
		PacienteDTO dto = new PacienteDTO();
		dto.setNome(paciente.getNome());
		dto.setCpf(paciente.getCpf());
		dto.setDataNascimento(paciente.getDataNascimento());
		dto.setTelefone(paciente.getTelefone());
		dto.setEmail(paciente.getEmail());
		return dto;
	}
}
