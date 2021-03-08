package com.java.jpa.app.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.java.jpa.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
//	CrudRepository class
//	public List<Cliente> findAll();
//	
//	public void save(Cliente cliente);
//	
//	public Cliente findById(Long id);
//	
//	public void deleteCliente(Long id);
	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchClienteByIdWithFacturas(Long id);
}
