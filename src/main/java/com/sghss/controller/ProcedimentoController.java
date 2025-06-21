package com.sghss.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sghss.dto.ProcedimentoDTO;
import com.sghss.service.ProcedimentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/procedimentos")
public class ProcedimentoController {

	private final ProcedimentoService service;

	public ProcedimentoController(ProcedimentoService service) {
		this.service = service;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO')")
	@PostMapping
	public ResponseEntity<ProcedimentoDTO> criar(@Valid @RequestBody ProcedimentoDTO dto) {
		return ResponseEntity.ok(service.salvar(dto));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA', 'PACIENTE')")
	@GetMapping
	public ResponseEntity<List<ProcedimentoDTO>> listar(@AuthenticationPrincipal UserDetails userDetails) {
		String email = userDetails.getUsername();

		if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("PACIENTE"))) {
			// Se for paciente, retorna apenas os seus procedimentos
			return ResponseEntity.ok(service.listarPorPacienteLogado(email));
		} else {
			// Outros perfis veem tudo
			return ResponseEntity.ok(service.listarTodos());
		}
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO', 'RECEPCIONISTA', 'PACIENTE')")
	@GetMapping("/{id}")
	public ResponseEntity<ProcedimentoDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO', 'ENFERMEIRO')")
	@PutMapping("/{id}")
	public ResponseEntity<ProcedimentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProcedimentoDTO dto) {
		return ResponseEntity.ok(service.atualizar(id, dto));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
