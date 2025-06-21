package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sghss.dto.ProfissionalSaudeDTO;
import com.sghss.exception.RecursoNaoEncontradoException;
import com.sghss.mapper.ProfissionalSaudeMapper;
import com.sghss.model.ProfissionalSaude;
import com.sghss.repository.ProfissionalSaudeRepository;

@Service
public class ProfissionalSaudeService {

	private final ProfissionalSaudeRepository repository;
	private final ProfissionalSaudeMapper mapper;

	public ProfissionalSaudeService(ProfissionalSaudeRepository repository, ProfissionalSaudeMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public ProfissionalSaudeDTO salvar(ProfissionalSaudeDTO dto) {
		ProfissionalSaude profissional = mapper.toEntity(dto);
		return mapper.toDTO(repository.save(profissional));
	}

	public List<ProfissionalSaudeDTO> listarTodos() {
		return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public ProfissionalSaudeDTO buscarPorId(Long id) {
		ProfissionalSaude profissional = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + id + " não encontrado"));
		return mapper.toDTO(profissional);
	}

	public ProfissionalSaudeDTO atualizar(Long id, ProfissionalSaudeDTO dto) {
		ProfissionalSaude profissional = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + id + " não encontrado"));

		profissional.setNome(dto.getNome());
		profissional.setEmail(dto.getEmail());
		profissional.setTelefone(dto.getTelefone());
		profissional.setCargo(dto.getCargo());
		profissional.setEspecialidade(dto.getEspecialidade());

		return mapper.toDTO(repository.save(profissional));
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Profissional com ID " + id + " não encontrado");
		}
		repository.deleteById(id);
	}
}
