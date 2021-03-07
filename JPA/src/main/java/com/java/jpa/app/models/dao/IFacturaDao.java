package com.java.jpa.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.java.jpa.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

}
