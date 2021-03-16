package com.apirest.backend.app.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.backend.app.model.dao.IClienteDao;
import com.apirest.backend.app.model.entity.Cliente;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

}
