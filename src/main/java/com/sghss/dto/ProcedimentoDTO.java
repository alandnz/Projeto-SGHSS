package com.sghss.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sghss.model.CodigoProcedimento;
import com.sghss.model.TipoProcedimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProcedimentoDTO {

	private Long id;

	@NotNull(message = "Paciente é obrigatório")
	private Long pacienteId;

	@NotNull(message = "Profissional é obrigatório")
	private Long profissionalId;

	@NotBlank(message = "Descrição é obrigatória")
	private String descricao;

	@NotNull(message = "Tipo do procedimento é obrigatório")
	private TipoProcedimento tipo;

	@NotNull(message = "Código do procedimento é obrigatório")
	private CodigoProcedimento codigo;

	@NotNull(message = "Data é obrigatória")
	private LocalDate data;

	@NotNull(message = "Horário é obrigatório")
	private LocalTime horario;

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

	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
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

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public String getProfissionalResponsavel() {
		return profissionalResponsavel;
	}

	public void setProfissionalResponsavel(String profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
}
