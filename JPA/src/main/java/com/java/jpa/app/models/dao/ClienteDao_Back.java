package com.java.jpa.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.java.jpa.app.models.entity.Cliente;

@Repository
public class ClienteDao_Back /*implements IClienteDao*/ {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
//	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

//	@Override
	public void save(Cliente cliente) {
		if( cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
	}

//	@Override
	public Cliente findById(Long id) {
		return em.find(Cliente.class, id);
	}

//	@Override
	public void deleteCliente(Long id) {
		em.remove(findById(id));
	}

}
