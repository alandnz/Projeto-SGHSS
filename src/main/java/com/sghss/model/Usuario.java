package com.sghss.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(unique = true)
	private String email;

	private String senha;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Perfil> perfis;

	@OneToOne
	@JoinColumn(name = "paciente_id", unique = true)
	private Paciente paciente;

	@OneToOne
	@JoinColumn(name = "profissional_id", unique = true)
	private ProfissionalSaude profissional;

	// Getters e Setters usuais
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

	// Implementação de UserDetails

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis.stream().map(perfil -> (GrantedAuthority) () -> "ROLE_" + perfil.name())
				.collect(Collectors.toSet());
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // Pode mudar para lógica de expiração
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // Pode mudar para lógica de bloqueio
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Pode mudar para lógica de expiração de credenciais
	}

	@Override
	public boolean isEnabled() {
		return true; // Pode mudar para lógica de ativação
	}
}
