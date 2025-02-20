package com.App.Lfarma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App.Lfarma.entity.Producto;
import com.App.Lfarma.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> buscarProductosPorIds(List<Long> ids) {
        return productoRepository.findAllById(ids);  
    }

    // Método para verificar si ya existe un producto con el mismo código
    public boolean existeProductoConCodigo(String codigoProducto) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigoProducto);
        return producto.isPresent();
    }

    public Producto guardarProducto(Producto producto) {
        // Verificar si ya existe un producto con el mismo código
        if (producto.getId() == null) {  // Para cuando es un producto nuevo
            if (existeProductoConCodigo(producto.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un producto con el código " + producto.getCodigo());
            }
            System.out.println("Guardando un nuevo producto: " + producto);
        } else {  // Para cuando es una actualización
            if (existeProductoConCodigo(producto.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un producto con el código " + producto.getCodigo());
            }
            System.out.println("Actualizando el producto con ID: " + producto.getId());
        }
        return productoRepository.save(producto);
    }

    public void eliminarPorCodigo(String codigoProducto) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigoProducto);
        if (producto.isPresent()) {
            productoRepository.delete(producto.get());
        } else {
            System.out.println("Producto no encontrado con el código: " + codigoProducto);
        }
    }

    public void descontarStock(String codigoProducto, int cantidadVendida) {
        Optional<Producto> productoOpt = productoRepository.findByCodigo(codigoProducto);
    
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getCantidad() >= cantidadVendida) {
                producto.setCantidad(producto.getCantidad() - cantidadVendida);
                productoRepository.save(producto);
            } else {
                System.out.println("Stock insuficiente para el producto con código: " + codigoProducto);
            }
        } else {
            System.out.println("Producto no encontrado con el código: " + codigoProducto);
        }
    }
}
