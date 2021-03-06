package com.java.jpa.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable page);
	
	public void save(Cliente cliente);
	
	public Cliente findById(Long id);
	
	public void deleteCliente(Long id);
	
	public List<Producto> findByNombre(String term);
}
