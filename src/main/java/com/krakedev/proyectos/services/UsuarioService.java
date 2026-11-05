package com.krakedev.proyectos.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario guardar(Usuario usuario) {
		String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
		usuario.setPassword(hash);
		return usuarioRepository.save(usuario);
	}

	public boolean autenticar(String username, String password) {
		Optional<Usuario> encontrado = usuarioRepository.findByUsername(username);
		if (encontrado.isPresent()) {
			return BCrypt.checkpw(password, encontrado.get().getPassword());
		}
		return false;
	}

	public Optional<Usuario> buscarPorUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}
}