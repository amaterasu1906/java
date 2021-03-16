package com.apirest.backend.app.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.apirest.backend.app.model.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
