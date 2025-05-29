package com.sghss.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sghss.dto.PacienteDTO;
import com.sghss.mapper.PacienteMapper;
import com.sghss.model.Paciente;
import com.sghss.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repository;

	public PacienteDTO salvar(PacienteDTO dto) {
		Paciente paciente = PacienteMapper.toEntity(dto);
		return PacienteMapper.toDTO(repository.save(paciente));
	}

	public List<PacienteDTO> listarTodos() {
		return repository.findAll().stream().map(PacienteMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<PacienteDTO> buscarPorId(Long id) {
		return repository.findById(id).map(PacienteMapper::toDTO);
	}

	public Optional<PacienteDTO> atualizar(Long id, PacienteDTO dto) {
		return repository.findById(id).map(p -> {
			p.setNome(dto.getNome());
			p.setCpf(dto.getCpf());
			p.setDataNascimento(dto.getDataNascimento());
			p.setTelefone(dto.getTelefone());
			p.setEmail(dto.getEmail());
			return PacienteMapper.toDTO(repository.save(p));
		});
	}

	public boolean deletar(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
}
