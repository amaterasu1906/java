package com.mockito.app.repository;

import java.util.List;

import com.mockito.app.models.Examen;

public interface IExamenRepository {

	List<Examen> findAll();
	
	Examen guardar(Examen examen);
}
