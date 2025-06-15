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

import com.sghss.dto.PacienteDTO;
import com.sghss.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

	@Autowired
	private PacienteService service;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO', 'ENFERMEIRO')")
	@PostMapping
	public ResponseEntity<PacienteDTO> cadastrar(@Valid @RequestBody PacienteDTO dto) {
		return ResponseEntity.ok(service.salvar(dto));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO', 'ENFERMEIRO')")
	@GetMapping
	public ResponseEntity<List<PacienteDTO>> listar() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO', 'ENFERMEIRO')")
	@GetMapping("/{id}")
	public ResponseEntity<PacienteDTO> buscar(@PathVariable Long id) {
		return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO', 'ENFERMEIRO')")
	@PutMapping("/{id}")
	public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO dto) {
		return service.atualizar(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
