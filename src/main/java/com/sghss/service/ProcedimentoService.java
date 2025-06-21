// ProcedimentoService.java
package com.sghss.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.exception.RecursoNaoEncontradoException;
import com.sghss.mapper.ProcedimentoMapper;
import com.sghss.model.CodigoProcedimento;
import com.sghss.model.Paciente;
import com.sghss.model.Procedimento;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.TipoProcedimento;
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

	private static final Set<LocalTime> HORARIOS_VALIDOS = gerarHorariosValidos();

	private static Set<LocalTime> gerarHorariosValidos() {
		return IntStream.rangeClosed(7 * 60, 20 * 60).filter(min -> min % 30 == 0)
				.mapToObj(min -> LocalTime.of(min / 60, min % 60)).collect(Collectors.toSet());
	}

	public ProcedimentoDTO salvar(ProcedimentoDTO dto) {
		validarHorario(dto.getHorario());
		validarTipoCodigo(dto.getTipo(), dto.getCodigo());

		Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(
				() -> new RecursoNaoEncontradoException("Paciente com ID " + dto.getPacienteId() + " não encontrado"));

		ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
				.orElseThrow(() -> new RecursoNaoEncontradoException(
						"Profissional com ID " + dto.getProfissionalId() + " não encontrado"));

		repository.findByPacienteIdAndDataAndHorario(paciente.getId(), dto.getData(), dto.getHorario()).ifPresent(p -> {
			throw new RuntimeException("Paciente já possui um procedimento nesse horário.");
		});

		repository.findByProfissionalIdAndDataAndHorario(profissional.getId(), dto.getData(), dto.getHorario())
				.ifPresent(p -> {
					throw new RuntimeException("Profissional já possui um procedimento nesse horário.");
				});

		repository.findByPacienteIdAndProfissionalIdAndDataAndHorario(paciente.getId(), profissional.getId(),
				dto.getData(), dto.getHorario()).ifPresent(p -> {
					throw new RuntimeException(
							"Já existe um procedimento com esse paciente, profissional, data e horário.");
				});

		Procedimento entidade = mapper.toEntity(dto, paciente, profissional);
		return mapper.toDTO(repository.save(entidade));
	}

	public ProcedimentoDTO atualizar(Long id, ProcedimentoDTO dto) {
		validarHorario(dto.getHorario());
		validarTipoCodigo(dto.getTipo(), dto.getCodigo());

		Procedimento existente = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Procedimento com ID " + id + " não encontrado"));

		Long pacienteId = Optional.ofNullable(dto.getPacienteId()).orElse(existente.getPaciente().getId());
		Long profissionalId = Optional.ofNullable(dto.getProfissionalId()).orElse(existente.getProfissional().getId());

		repository.findByPacienteIdAndDataAndHorario(pacienteId, dto.getData(), dto.getHorario())
				.filter(p -> !p.getId().equals(id)).ifPresent(p -> {
					throw new RuntimeException("Paciente já possui outro procedimento nesse horário.");
				});

		repository.findByProfissionalIdAndDataAndHorario(profissionalId, dto.getData(), dto.getHorario())
				.filter(p -> !p.getId().equals(id)).ifPresent(p -> {
					throw new RuntimeException("Profissional já possui outro procedimento nesse horário.");
				});

		repository.findByPacienteIdAndProfissionalIdAndDataAndHorario(pacienteId, profissionalId, dto.getData(),
				dto.getHorario()).filter(p -> !p.getId().equals(id)).ifPresent(p -> {
					throw new RuntimeException(
							"Já existe um procedimento com esse paciente, profissional, data e horário.");
				});

		existente.setDescricao(dto.getDescricao());
		existente.setTipo(dto.getTipo());
		existente.setCodigo(dto.getCodigo());
		existente.setData(dto.getData());
		existente.setHorario(dto.getHorario());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Paciente com ID " + dto.getPacienteId() + " não encontrado"));
			existente.setPaciente(paciente);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Profissional com ID " + dto.getProfissionalId() + " não encontrado"));
			existente.setProfissional(profissional);
		}

		return mapper.toDTO(repository.save(existente));
	}

	private void validarHorario(LocalTime horario) {
		if (horario == null || !HORARIOS_VALIDOS.contains(horario)) {
			throw new RuntimeException(
					"Horário inválido. Os horários permitidos são de 07:00 às 20:00 com intervalos de 30 minutos.");
		}
	}

	private void validarTipoCodigo(TipoProcedimento tipo, CodigoProcedimento codigo) {
		if (tipo == null || codigo == null)
			return;
		if (!tipo.getCodigoPadrao().equals(codigo)) {
			throw new RuntimeException("O tipo " + tipo + " deve estar vinculado ao código " + tipo.getCodigoPadrao());
		}
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

	public ProcedimentoDTO buscarPorId(Long id) {
		return repository.findById(id).map(mapper::toDTO)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Procedimento com ID " + id + " não encontrado"));
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Procedimento com ID " + id + " não encontrado");
		}
		repository.deleteById(id);
	}
}
