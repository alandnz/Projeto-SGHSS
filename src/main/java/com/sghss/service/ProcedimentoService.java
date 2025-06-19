package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.exception.RecursoNaoEncontradoException;
import com.sghss.mapper.ProcedimentoMapper;
import com.sghss.model.Paciente;
import com.sghss.model.Procedimento;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.Usuario;
import com.sghss.repository.PacienteRepository;
import com.sghss.repository.ProcedimentoRepository;
import com.sghss.repository.ProfissionalSaudeRepository;
import com.sghss.repository.UsuarioRepository;

@Service
public class ProcedimentoService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private ProfissionalSaudeRepository profissionalRepository;

	@Autowired
	private ProcedimentoRepository repository;

	@Autowired
	private ProcedimentoMapper mapper;

	public ProcedimentoDTO salvar(ProcedimentoDTO dto) {
		Paciente paciente = null;
		ProfissionalSaude profissional = null;

		if (dto.getPacienteId() != null) {
			paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Paciente com ID " + dto.getPacienteId() + " não encontrado"));
		}

		if (dto.getProfissionalId() != null) {
			profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Profissional com ID " + dto.getProfissionalId() + " não encontrado"));
		}

		Procedimento entidade = mapper.toEntity(dto, paciente, profissional);
		return mapper.toDTO(repository.save(entidade));
	}

	public List<ProcedimentoDTO> listarTodos() {
		return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public List<ProcedimentoDTO> listarPorPacienteLogado(String emailPaciente) {
		Usuario usuario = usuarioRepository.findByEmail(emailPaciente).orElseThrow(
				() -> new RecursoNaoEncontradoException("Usuário com e-mail " + emailPaciente + " não encontrado"));

		if (usuario.getPaciente() == null) {
			throw new RecursoNaoEncontradoException("Usuário não está vinculado a um procedimento");
		}

		Long pacienteId = usuario.getPaciente().getId();
		List<Procedimento> procedimentos = repository.findByPacienteId(pacienteId);
		return procedimentos.stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public ProcedimentoDTO atualizar(Long id, ProcedimentoDTO dto) {
		Procedimento existente = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Procedimento com ID " + id + " não encontrado"));

		existente.setDescricao(dto.getDescricao());
		existente.setTipo(dto.getTipo());
		existente.setCodigo(dto.getCodigo());
		existente.setData(dto.getData());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Paciente com ID " + dto.getPacienteId() + " não encontrado"));
			existente.setPaciente(paciente);
		} else {
			existente.setPaciente(null);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Profissional com ID " + dto.getProfissionalId() + " não encontrado"));
			existente.setProfissional(profissional);
		} else {
			existente.setProfissional(null);
		}

		return mapper.toDTO(repository.save(existente));
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Procedimento com ID " + id + " não encontrado");
		}
		repository.deleteById(id);
	}
}
