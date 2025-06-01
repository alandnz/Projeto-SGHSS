package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sghss.dto.UsuarioDTO;
import com.sghss.mapper.UsuarioMapper;
import com.sghss.model.Usuario;
import com.sghss.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

	public UsuarioDTO salvar(UsuarioDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto);
		usuario.setSenha(encoder.encode(dto.getSenha()));
		return UsuarioMapper.toDTO(repository.save(usuario));
	}

	public List<UsuarioDTO> listarTodos() {
		return repository.findAll().stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
	}

	public UsuarioDTO buscarPorId(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		return UsuarioMapper.toDTO(usuario);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
