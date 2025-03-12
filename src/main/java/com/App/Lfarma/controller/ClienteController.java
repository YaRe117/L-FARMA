package com.App.Lfarma.controller;

import com.App.Lfarma.entity.Cliente;
import com.App.Lfarma.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @PostMapping("/agregar")
    public String agregarCliente(Cliente cliente) {
        clienteService.agregarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable String codigo, Model model) {
        Cliente cliente = clienteService.obtenerClientePorCodigo(codigo).orElse(null);
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @PostMapping("/actualizar")
    public String actualizarCliente(Cliente cliente) {
        clienteService.actualizarCliente(cliente);
        return "redirect:/clientes";
    }

    
    @PostMapping("/eliminar")
    public String eliminarCliente(@RequestParam String codigo) {
        clienteService.eliminarCliente(codigo);
        return "redirect:/clientes";
    }
}