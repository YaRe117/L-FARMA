package com.App.Lfarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.App.Lfarma.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
