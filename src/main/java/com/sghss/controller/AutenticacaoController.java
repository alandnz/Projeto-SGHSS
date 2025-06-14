package com.sghss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sghss.dto.LoginRequestDTO;
import com.sghss.dto.TokenResponseDTO;
import com.sghss.security.JwtService;
import com.sghss.security.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@PostMapping("/login")
	public TokenResponseDTO autenticar(@RequestBody LoginRequestDTO request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = jwtService.gerarToken(userDetails);

		return new TokenResponseDTO(token);
	}
}
