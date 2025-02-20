package com.App.Lfarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App.Lfarma.entity.Factura;
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

}
