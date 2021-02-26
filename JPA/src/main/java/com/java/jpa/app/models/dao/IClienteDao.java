package com.java.jpa.app.models.dao;


import org.springframework.data.repository.CrudRepository;
import com.java.jpa.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

//	public List<Cliente> findAll();
//	
//	public void save(Cliente cliente);
//	
//	public Cliente findById(Long id);
//	
//	public void deleteCliente(Long id);
}
