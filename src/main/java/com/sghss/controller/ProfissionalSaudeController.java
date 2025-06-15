package com.sghss.controller;

import java.util.List;

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

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalSaudeController {

	private final ProfissionalSaudeService service;

	public ProfissionalSaudeController(ProfissionalSaudeService service) {
		this.service = service;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA')")
	@PostMapping
	public ProfissionalSaudeDTO salvar(@RequestBody ProfissionalSaudeDTO dto) {
		return service.salvar(dto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA', 'PACIENTE')")
	@GetMapping
	public List<ProfissionalSaudeDTO> listarTodos() {
		return service.listarTodos();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA')")
	@PutMapping("/{id}")
	public ProfissionalSaudeDTO atualizar(@PathVariable Long id, @RequestBody ProfissionalSaudeDTO dto) {
		return service.atualizar(id, dto);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
}
