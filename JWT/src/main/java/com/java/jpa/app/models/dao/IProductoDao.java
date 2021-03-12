package com.java.jpa.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.java.jpa.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
//	Segunda opcion, busqueda por nombre de metodo
	public List<Producto> findByNombreLikeIgnoreCase(String term);
	
}
