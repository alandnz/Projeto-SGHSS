package com.sghss.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Procedimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "paciente_id", nullable = false)
	private Paciente paciente;

	@ManyToOne
	@JoinColumn(name = "profissional_id", nullable = false)
	private ProfissionalSaude profissional;

	@Column(nullable = false)
	private LocalDate data;

	@Column(nullable = false)
	private LocalTime horario;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoProcedimento tipo;

	@Enumerated(EnumType.STRING)
	private CodigoProcedimento codigo;

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public ProfissionalSaude getProfissional() {
		return profissional;
	}

	public void setProfissional(ProfissionalSaude profissional) {
		this.profissional = profissional;
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
}
