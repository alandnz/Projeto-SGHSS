package com.sghss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public UsuarioDTO criar(@RequestBody UsuarioDTO dto) {
		return usuarioService.salvar(dto);
	}

	@GetMapping
	public List<UsuarioDTO> listarTodos() {
		return usuarioService.listarTodos();
	}

	@GetMapping("/{id}")
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return usuarioService.buscarPorId(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
		UsuarioDTO atualizado = usuarioService.atualizarUsuario(id, dto);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
	}
}
