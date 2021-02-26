package com.java.jpa.app.services;

import java.util.List;

import com.java.jpa.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findById(Long id);
	
	public void deleteCliente(Long id);
}
