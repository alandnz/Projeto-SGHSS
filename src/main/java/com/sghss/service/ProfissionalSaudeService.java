package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sghss.dto.ProfissionalSaudeDTO;
import com.sghss.mapper.ProfissionalSaudeMapper;
import com.sghss.model.ProfissionalSaude;
import com.sghss.repository.ProfissionalSaudeRepository;

@Service
public class ProfissionalSaudeService {

	private final ProfissionalSaudeRepository repository;

	public ProfissionalSaudeService(ProfissionalSaudeRepository repository) {
		this.repository = repository;
	}

	public ProfissionalSaudeDTO salvar(ProfissionalSaudeDTO dto) {
		ProfissionalSaude entity = ProfissionalSaudeMapper.toEntity(dto);
		return ProfissionalSaudeMapper.toDTO(repository.save(entity));
	}

	public List<ProfissionalSaudeDTO> listarTodos() {
		return repository.findAll().stream().map(ProfissionalSaudeMapper::toDTO).collect(Collectors.toList());
	}

	public ProfissionalSaudeDTO atualizar(Long id, ProfissionalSaudeDTO dto) {
		ProfissionalSaude entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
		entity.setNome(dto.getNome());
		entity.setCargo(dto.getCargo());
		entity.setEmail(dto.getEmail());
		entity.setTelefone(dto.getTelefone());
		entity.setEspecialidade(dto.getEspecialidade());
		return ProfissionalSaudeMapper.toDTO(repository.save(entity));
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
