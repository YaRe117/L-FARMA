package com.App.Lfarma.service;

import org.springframework.stereotype.Service;

import com.App.Lfarma.entity.Usuario;
import com.App.Lfarma.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(usuario.getUsername(), usuario.getPassword(),
            Arrays.asList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())));
    }
}
