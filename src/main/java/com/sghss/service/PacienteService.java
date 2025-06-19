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

	public java.util.Optional<PacienteDTO> buscarPorId(Long id) {
		return repository.findById(id).map(mapper::toDTO);
	}

	public java.util.Optional<PacienteDTO> atualizar(Long id, PacienteDTO dto) {
		Paciente paciente = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado"));

		paciente.setNome(dto.getNome());
		paciente.setCpf(dto.getCpf());
		paciente.setDataNascimento(dto.getDataNascimento());
		paciente.setTelefone(dto.getTelefone());

		return java.util.Optional.of(mapper.toDTO(repository.save(paciente)));
	}

	public boolean deletar(Long id) {
		if (!repository.existsById(id)) {
			return false;
		}
		repository.deleteById(id);
		return true;
	}
}
