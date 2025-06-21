package com.sghss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sghss.dto.UsuarioDTO;
import com.sghss.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
		return ResponseEntity.ok(usuarioService.salvar(dto));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listarTodos() {
		return ResponseEntity.ok(usuarioService.listarTodos());
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.buscarPorId(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
		return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
