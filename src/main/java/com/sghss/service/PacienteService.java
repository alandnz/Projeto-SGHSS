package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sghss.dto.PacienteDTO;
import com.sghss.exception.RecursoNaoEncontradoException;
import com.sghss.mapper.PacienteMapper;
import com.sghss.model.Paciente;
import com.sghss.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repository;

	@Autowired
	private PacienteMapper mapper;

	public PacienteDTO salvar(PacienteDTO dto) {
		Paciente paciente = mapper.toEntity(dto);
		return mapper.toDTO(repository.save(paciente));
	}

	public List<PacienteDTO> listarTodos() {
		return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public PacienteDTO buscarPorId(Long id) {
		Paciente paciente = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado"));
		return mapper.toDTO(paciente);
	}

	public PacienteDTO atualizar(Long id, PacienteDTO dto) {
		Paciente paciente = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado"));

		paciente.setNome(dto.getNome());
		paciente.setCpf(dto.getCpf());
		paciente.setDataNascimento(dto.getDataNascimento());
		paciente.setTelefone(dto.getTelefone());

		return mapper.toDTO(repository.save(paciente));
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado");
		}
		repository.deleteById(id);
	}
}
