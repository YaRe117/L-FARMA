package com.App.Lfarma.controller;

import com.App.Lfarma.entity.Usuario;
import com.App.Lfarma.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password"))
            );
            return "Login exitoso para: " + authentication.getName();
        } catch (AuthenticationException e) {
            return "Error: Credenciales inválidas";
        }
    }

    @PostMapping("/register")
public String register(@RequestBody Usuario usuario) {
    // Encriptar la contraseña antes de guardar el usuario
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    
    usuarioRepository.save(usuario);
    return "Usuario registrado correctamente";
}

    @GetMapping("/dashboard_admin")
    public String dashboardAdmin() {
        return "dashboard_admin";
    }

    @GetMapping("/dashboard_empleado")
    public String dashboardEmpleado() {
        return "dashboard_empleado";
    }
}



