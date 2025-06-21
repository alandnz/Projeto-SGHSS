package com.sghss.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PacienteDTO {

	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "CPF é obrigatório")
	private String cpf;

	@NotNull(message = "Data de nascimento é obrigatória")
	private LocalDate dataNascimento;

	@NotBlank(message = "Telefone é obrigatório")
	private String telefone;

	@Email(message = "E-mail inválido")
	@NotBlank(message = "E-mail é obrigatório")
	private String email;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
