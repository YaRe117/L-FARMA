package com.App.Lfarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.App.Lfarma.entity.Producto;
import com.App.Lfarma.service.ProductoService;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "visualizar-productos";
    }

    @GetMapping("/registrar-productos")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("producto", new Producto());
        return "registrar-productos";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoService.guardarProducto(producto);
            return "redirect:/productos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registrar-productos";  // Regresa al formulario de registro con el error
        }
    }

    @GetMapping("/actualizar-productos")
    public String mostrarFormularioEditar(@RequestParam("codigoProducto") String codigoProducto, Model model) {
        Optional<Producto> producto = productoService.buscarPorCodigo(codigoProducto);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "actualizar-productos";
        } else {
            return "redirect:/productos";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoService.guardarProducto(producto);
            return "redirect:/productos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "actualizar-productos";  // Regresa al formulario de edición con el error
        }
    }

    @GetMapping("/eliminar-productos")
    public String mostrarFormularioEliminar(Model model) {
        model.addAttribute("mensaje", "");
        return "eliminar-productos";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam("codigoProducto") String codigoProducto, Model model) {
        Optional<Producto> producto = productoService.buscarPorCodigo(codigoProducto);

        if (producto.isPresent()) {
            productoService.eliminarPorCodigo(codigoProducto);
            model.addAttribute("mensaje", "Producto eliminado correctamente");
        } else {
            model.addAttribute("mensaje", "Producto no encontrado");
        }

        return "redirect:/productos"; 
    }

    @GetMapping("/buscar-productos")
    public String mostrarFormularioBuscar(Model model) {
        model.addAttribute("producto", new Producto());
        return "buscar-productos";
    }

    @PostMapping("/buscar")
    public String buscarProductoPorCodigo(@RequestParam("codigoProducto") String codigoProducto, Model model) {
        Optional<Producto> producto = productoService.buscarPorCodigo(codigoProducto);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "resultado-busqueda";
        } else {
            model.addAttribute("mensaje", "Producto no encontrado");
            return "buscar-productos";
        }
    }
}
