package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sghss.dto.ProcedimentoDTO;
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

	private final UsuarioRepository usuarioRepository;
	private final PacienteRepository pacienteRepository;
	private final ProfissionalSaudeRepository profissionalRepository;
	private final ProcedimentoRepository repository;

	private final ProcedimentoMapper mapper;

	@Autowired
	public ProcedimentoService(ProcedimentoRepository repository, PacienteRepository pacienteRepository,
			ProfissionalSaudeRepository profissionalRepository, UsuarioRepository usuarioRepository,
			ProcedimentoMapper mapper) {
		this.repository = repository;
		this.pacienteRepository = pacienteRepository;
		this.profissionalRepository = profissionalRepository;
		this.usuarioRepository = usuarioRepository;
		this.mapper = mapper;
	}

	public ProcedimentoDTO salvar(ProcedimentoDTO dto) {
		Procedimento entidade = mapper.toEntity(dto, pacienteRepository, profissionalRepository);
		return mapper.toDTO(repository.save(entidade));
	}

	public List<ProcedimentoDTO> listarTodos() {
		return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public List<ProcedimentoDTO> listarPorPacienteLogado(String emailPaciente) {
		Usuario usuario = usuarioRepository.findByEmail(emailPaciente)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		if (usuario.getPaciente() == null) {
			throw new RuntimeException("Usuário não está vinculado a um paciente.");
		}

		Long pacienteId = usuario.getPaciente().getId();

		List<Procedimento> procedimentos = repository.findByPacienteId(pacienteId);

		return procedimentos.stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public ProcedimentoDTO atualizar(Long id, ProcedimentoDTO dto) {
		Procedimento existente = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

		existente.setDescricao(dto.getDescricao());
		existente.setTipo(dto.getTipo());
		existente.setCodigo(dto.getCodigo());
		existente.setData(dto.getData());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
			existente.setPaciente(paciente);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
			existente.setProfissional(profissional);
		}

		return mapper.toDTO(repository.save(existente));
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
