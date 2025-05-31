package com.sghss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sghss.model.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}
