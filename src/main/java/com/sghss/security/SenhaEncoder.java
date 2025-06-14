package com.sghss.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaEncoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCriptografada = encoder.encode("admin123");
		System.out.println("Senha criptografada: " + senhaCriptografada);
	}
}
