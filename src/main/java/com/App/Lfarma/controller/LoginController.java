package com.App.Lfarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.App.Lfarma.entity.Usuario;
import com.App.Lfarma.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("username")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        Usuario usuario = usuarioService.autenticar(username, password);

        if (usuario != null) {
            session.setAttribute("username", usuario.getUsername());
            session.setAttribute("rol", usuario.getRol());

            if ("admin".equals(usuario.getRol())) {
                return "redirect:/dashboard_admin";
            } else {
                return "redirect:/dashboard_empleado";
            }
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
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
