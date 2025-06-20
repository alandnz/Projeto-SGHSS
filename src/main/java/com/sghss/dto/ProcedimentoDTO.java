package com.sghss.dto;

import java.time.LocalDate;

import com.sghss.model.CodigoProcedimento;
import com.sghss.model.TipoProcedimento;

public class ProcedimentoDTO {
	private Long id;
	private Long pacienteId;
	private Long profissionalId;
	private String descricao;
	private TipoProcedimento tipo;
	private CodigoProcedimento codigo;
	private LocalDate data;
	private String profissionalResponsavel;
	private String nomePaciente;

	// Getters e Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoProcedimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoProcedimento tipo) {
		this.tipo = tipo;
	}

	public CodigoProcedimento getCodigo() {
		return codigo;
	}

	public void setCodigo(CodigoProcedimento codigo) {
		this.codigo = codigo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getProfissionalResponsavel() {
		return profissionalResponsavel;
	}

	public void setProfissionalResponsavel(String profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
	}

	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

}
