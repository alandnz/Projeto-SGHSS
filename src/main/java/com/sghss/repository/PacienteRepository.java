package com.sghss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sghss.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
