package com.java.jpa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.jpa.app.models.dao.IClienteDao;
import com.java.jpa.app.models.dao.IProductoDao;
import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.models.entity.Producto;

@Service
public class ClienteService implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void deleteCliente(Long id) {
		clienteDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Cliente> findAll(Pageable page) {
		return clienteDao.findAll(page);
	}

	@Override
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}

}
