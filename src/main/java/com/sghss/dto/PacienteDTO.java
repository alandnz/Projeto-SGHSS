package com.sghss.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PacienteDTO {

	@NotBlank(message = "O nome é obrigatório.")
	private String nome;

	@NotBlank(message = "O CPF é obrigatório.")
	@Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas 11 dígitos numéricos.")
	private String cpf;

	@NotNull(message = "A data de nascimento é obrigatória.")
	private LocalDate dataNascimento;

	@Size(min = 10, max = 15, message = "O telefone deve ter entre 10 e 15 caracteres.")
	private String telefone;

	@Email(message = "E-mail inválido.")
	private String email;

	// Getters e setters
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
