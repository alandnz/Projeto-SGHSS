package com.sghss.controller;

import java.util.List;

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

import com.sghss.dto.ProfissionalSaudeDTO;
import com.sghss.service.ProfissionalSaudeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalSaudeController {

	private final ProfissionalSaudeService service;

	public ProfissionalSaudeController(ProfissionalSaudeService service) {
		this.service = service;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA')")
	@PostMapping
	public ResponseEntity<ProfissionalSaudeDTO> salvar(@Valid @RequestBody ProfissionalSaudeDTO dto) {
		return ResponseEntity.ok(service.salvar(dto));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA', 'PACIENTE')")
	@GetMapping
	public ResponseEntity<List<ProfissionalSaudeDTO>> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA')")
	@GetMapping("/{id}")
	public ResponseEntity<ProfissionalSaudeDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA')")
	@PutMapping("/{id}")
	public ResponseEntity<ProfissionalSaudeDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProfissionalSaudeDTO dto) {
		return ResponseEntity.ok(service.atualizar(id, dto));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
