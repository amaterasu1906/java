package com.mockito.app.services;

import java.util.Optional;

import com.mockito.app.models.Examen;

public interface IExamenService {

	Optional<Examen> findExamenPorNombre(String nombre);
	Examen findExamenPorNombreConPreguntas(String nombre);
	
	Examen guardar(Examen examen);
}
