package com.sghss.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sghss.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		if (path.equals("/api/auth/login")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			String email = jwtService.extrairEmail(jwt);

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails usuario = usuarioService.loadUserByUsername(email);

				if (jwtService.tokenValido(jwt, usuario)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario,
							null, usuario.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
