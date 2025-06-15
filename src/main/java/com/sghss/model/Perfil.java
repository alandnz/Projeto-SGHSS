package com.sghss.model;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
	ADMIN, RECEPCIONISTA, MEDICO, ENFERMEIRO, PACIENTE;

	@Override
	public String getAuthority() {
		return name();
	}
}
