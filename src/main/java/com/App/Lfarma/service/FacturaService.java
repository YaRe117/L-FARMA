package com.App.Lfarma.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.App.Lfarma.entity.Cliente;
import com.App.Lfarma.entity.DetalleFactura;
import com.App.Lfarma.entity.Factura;
import com.App.Lfarma.entity.Producto;
import com.App.Lfarma.repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private FacturaRepository facturaRepository;

    public Factura crearFactura(Cliente cliente, List<DetalleFactura> detalles) {
        double total = 0;
        double iva = 0.19;

        Factura factura = new Factura();
        factura.setFecha(new Date());
        factura.setCliente(cliente);
        factura.setDetalles(detalles);

        for (DetalleFactura detalle : detalles) {
            Producto producto = detalle.getProducto();
            if (producto.getCantidad() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            productoService.descontarStock(producto.getCodigo(), detalle.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setFactura(factura);
            total += detalle.getCantidad() * detalle.getPrecioUnitario();
        }

        factura.setTotal(total + (total * iva));
        factura.setIva(total * iva);

        return facturaRepository.save(factura);
    }

    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }
}
