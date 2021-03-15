package com.apirest.backend.app.model.services;

import java.util.List;

import com.apirest.backend.app.model.entity.Cliente;

public interface IClienteService {
	public Cliente findById(Long id);
	
	public List<Cliente> findAll();

	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
}
