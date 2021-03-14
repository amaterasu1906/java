package com.apirest.backend.app.model.services;

import java.util.List;

import com.apirest.backend.app.model.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();

}
