package com.mockito.app.services;

import java.util.Arrays;
import java.util.List;

import com.mockito.app.models.Examen;

public class Datos {
	
	public static final List<Examen> EXAMENES = Arrays.asList(
			new Examen(5L, "Matematicas"),
			new Examen(6L, "Idiomas"),
			new Examen(7L, "Historia")
			);
	
	public static final List<String> PREGUNTAS = Arrays.asList(
			"Aritmetica",
			"Integrales",
			"Derivadas",
			"Trigonometria",
			"Geometria"
			);
	public static final Examen EXAMEN = new Examen(null, "Fisica");
//	public static final Examen EXAMEN = new Examen(8L, "Fisica");
}
