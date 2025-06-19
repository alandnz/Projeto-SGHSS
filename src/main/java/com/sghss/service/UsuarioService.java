package com.sghss.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sghss.dto.UsuarioDTO;
import com.sghss.exception.RecursoNaoEncontradoException;
import com.sghss.mapper.UsuarioMapper;
import com.sghss.model.Paciente;
import com.sghss.model.Perfil;
import com.sghss.model.ProfissionalSaude;
import com.sghss.model.Usuario;
import com.sghss.repository.PacienteRepository;
import com.sghss.repository.ProfissionalSaudeRepository;
import com.sghss.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

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
		validarVinculos(dto);
		Usuario usuario = usuarioMapper.toEntity(dto);
		usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
		return usuarioMapper.toDTO(usuarioRepository.save(usuario));
	}

	public List<UsuarioDTO> listarTodos() {
		return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).collect(Collectors.toList());
	}

	public UsuarioDTO buscarPorId(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
		return usuarioMapper.toDTO(usuario);
	}

	public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO dto) {
		validarVinculos(dto);
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado"));

		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
		usuario.setPerfis(dto.getPerfis());

		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Paciente com ID " + dto.getPacienteId() + " não encontrado"));
			usuario.setPaciente(paciente);
		} else {
			usuario.setPaciente(null);
		}

		if (dto.getProfissionalId() != null) {
			ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
					.orElseThrow(() -> new RecursoNaoEncontradoException(
							"Profissional com ID " + dto.getProfissionalId() + " não encontrado"));
			usuario.setProfissional(profissional);
		} else {
			usuario.setProfissional(null);
		}

		usuario = usuarioRepository.save(usuario);
		return usuarioMapper.toDTO(usuario);
	}

	public void deletar(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado para exclusão");
		}
		usuarioRepository.deleteById(id);
	}

	private void validarVinculos(UsuarioDTO dto) {
		Set<Perfil> perfis = dto.getPerfis();

		if (perfis.contains(Perfil.PACIENTE)) {
			if (dto.getPacienteId() == null || !pacienteRepository.existsById(dto.getPacienteId())) {
				throw new RecursoNaoEncontradoException("Perfil PACIENTE exige um pacienteId válido");
			}
		}

		if (perfis.contains(Perfil.MEDICO) || perfis.contains(Perfil.ENFERMEIRO)) {
			if (dto.getProfissionalId() == null || !profissionalRepository.existsById(dto.getProfissionalId())) {
				throw new RecursoNaoEncontradoException("Perfis MÉDICO ou ENFERMEIRO exigem um profissionalId válido");
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + email + " não encontrado"));
	}
}
