package com.sghss.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sghss.model.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

	List<Procedimento> findByPacienteId(Long pacienteId);

	// Verifica se já existe procedimento com mesmo paciente, profissional, data e
	// horário

	Optional<Procedimento> findByPacienteIdAndProfissionalIdAndDataAndHorario(Long pacienteId, Long profissionalId,
			LocalDate data, LocalTime horario);

	// Verifica se já existe outro procedimento no mesmo horário para o mesmo
	// paciente

	Optional<Procedimento> findByPacienteIdAndDataAndHorario(Long pacienteId, LocalDate data, LocalTime horario);

	// Verifica se já existe outro procedimento no mesmo horário para o mesmo
	// profissional

	Optional<Procedimento> findByProfissionalIdAndDataAndHorario(Long profissionalId, LocalDate data,
			LocalTime horario);
}
