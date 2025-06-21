package com.sghss.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ProfissionalSaudeDTO {

	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "Cargo é obrigatório")
	private String cargo;

	@Email(message = "E-mail inválido")
	@NotBlank(message = "E-mail é obrigatório")
	private String email;

	@NotBlank(message = "Telefone é obrigatório")
	private String telefone;

	@NotBlank(message = "Especialidade é obrigatória")
	private String especialidade;

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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
}
