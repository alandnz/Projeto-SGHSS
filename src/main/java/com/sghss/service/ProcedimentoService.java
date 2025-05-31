package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.mapper.ProcedimentoMapper;
import com.sghss.model.Procedimento;
import com.sghss.repository.ProcedimentoRepository;

@Service
public class ProcedimentoService {

	private final ProcedimentoRepository repository;
	private final ProcedimentoMapper mapper;

	public ProcedimentoService(ProcedimentoRepository repository, ProcedimentoMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public ProcedimentoDTO salvar(ProcedimentoDTO dto) {
		Procedimento entidade = mapper.toEntity(dto);
		return mapper.toDTO(repository.save(entidade));
	}

	public List<ProcedimentoDTO> listarTodos() {
		return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public ProcedimentoDTO atualizar(Long id, ProcedimentoDTO dto) {
		Procedimento existente = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

		existente.setDescricao(dto.getDescricao());
		existente.setTipo(dto.getTipo());
		existente.setCodigo(dto.getCodigo());
		existente.setData(dto.getData());
		existente.setProfissionalResponsavel(dto.getProfissionalResponsavel());

		// Atualizar o paciente (opcionalmente)
		if (dto.getPacienteId() != null) {
			existente.setPaciente(mapper.toEntity(dto).getPaciente());
		}

		return mapper.toDTO(repository.save(existente));
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
