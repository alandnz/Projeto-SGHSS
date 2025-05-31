package com.sghss.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {

	private final ProcedimentoService service;

	public ProcedimentoController(ProcedimentoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ProcedimentoDTO> criar(@RequestBody ProcedimentoDTO dto) {
		return ResponseEntity.ok(service.salvar(dto));
	}

	@GetMapping
	public ResponseEntity<List<ProcedimentoDTO>> listar() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProcedimentoDTO> atualizar(@PathVariable Long id, @RequestBody ProcedimentoDTO dto) {
		return ResponseEntity.ok(service.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
