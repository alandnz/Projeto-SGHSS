package com.sghss.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sghss.model.Perfil;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {

	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@Email(message = "E-mail inválido")
	@NotBlank(message = "E-mail é obrigatório")
	private String email;

	@NotBlank(message = "Senha é obrigatória")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String senha;

	@NotNull(message = "É necessário informar pelo menos um perfil")
	private Set<@NotNull(message = "Perfil inválido") Perfil> perfis;

	private Long pacienteId;
	private Long profissionalId;

	// Getters e Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
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
}
