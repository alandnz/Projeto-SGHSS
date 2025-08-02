package com.sghss.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("timestamp", LocalDateTime.now(), "status", 403,
				"error", "Ação não permitida", "message", "Você não tem permissão para realizar esta ação."));
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<?> handleNotFound(RecursoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("timestamp", LocalDateTime.now(), "status", 404,
				"error", "Recurso não encontrado", "message", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		var campo = ex.getBindingResult().getFieldErrors().get(0);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("timestamp", LocalDateTime.now(), "status", 400, "error", "Erro de validação", "message",
						"Campo '" + campo.getField() + "': " + campo.getDefaultMessage()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntime(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("timestamp", LocalDateTime.now(), "status",
				400, "error", "Erro na requisição", "message", ex.getMessage()));
	}
}
