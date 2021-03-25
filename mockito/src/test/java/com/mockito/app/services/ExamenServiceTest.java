package com.mockito.app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import com.mockito.app.models.Examen;
//import com.mockito.app.repository.ExamenRepository;
import com.mockito.app.repository.IExamenRepository;
import com.mockito.app.repository.IPreguntaRepository;

class ExamenServiceTest {
	IExamenRepository repo;
	IExamenService service;
	IPreguntaRepository preguntaRepository;
	
	@BeforeEach
	void initTest() {
		repo = mock(IExamenRepository.class);
		preguntaRepository = mock(IPreguntaRepository.class);
		service = new ExamenService(repo, preguntaRepository);
	}
	@Test
	void test() {
//		IExamenRepository repo = new ExamenRepository();
		
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
		assertNotNull(examen);
		assertEquals(5L, examen.get().getId());
		assertEquals("Matematicas", examen.get().getNombre());
	}
	@Test
	void testEmpty() {
		List<Examen> datos = Collections.emptyList();
		when(repo.findAll()).thenReturn(datos);
		Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
		assertFalse(examen.isPresent());
	}

	@Test
	void testPreguntaExamen() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
//		Para cualquier valor numerico se aplica el mock
		when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
		.thenReturn(Datos.PREGUNTAS);
		
		Examen examen = service.findExamenPorNombreConPreguntas("Historia");
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("Aritmetica"));
	}
}
