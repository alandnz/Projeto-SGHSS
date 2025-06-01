package com.sghss.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sghss.dto.UsuarioDTO;
import com.sghss.mapper.UsuarioMapper;
import com.sghss.model.Paciente;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.Usuario;
import com.sghss.repository.PacienteRepository;
import com.sghss.repository.ProfissionalSaudeRepository;
import com.sghss.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private ProfissionalSaudeRepository profissionalRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UsuarioDTO salvar(UsuarioDTO dto) {
		Usuario usuario = usuarioMapper.toEntity(dto);
		usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // garante criptografia no POST também
		return usuarioMapper.toDTO(usuarioRepository.save(usuario));
	}

	public List<UsuarioDTO> listarTodos() {
		return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).collect(Collectors.toList());
	}

	public UsuarioDTO buscarPorId(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		return usuarioMapper.toDTO(usuario);
	}

	public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO dto) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

		usuario.setPerfis(dto.getPerfis());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
			usuario.setPaciente(paciente);
		} else {
			usuario.setPaciente(null);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
			usuario.setProfissional(profissional);
		} else {
			usuario.setProfissional(null);
		}

		usuario = usuarioRepository.save(usuario);
		return usuarioMapper.toDTO(usuario);
	}

	public void deletar(Long id) {
		usuarioRepository.deleteById(id);
	}
}
