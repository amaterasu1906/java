package com.mockito.app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import com.mockito.app.models.Examen;
//import com.mockito.app.repository.ExamenRepository;
import com.mockito.app.repository.IExamenRepository;
import com.mockito.app.repository.IPreguntaRepository;

//Opcion 2, con extends, depende de mockito-junit-jupiter
@ExtendWith(MockitoExtension.class)
class ExamenServiceTest {
	
	@Mock
	IExamenRepository repo;
	
	@Mock
	IPreguntaRepository preguntaRepository;

//	Necesita una instancia implementada no interface
	@InjectMocks
	ExamenService service;
	
//	Opcion 1 para crear mockito e inyectar dependencias
	/*
	@BeforeEach
	void initTest() {
		MockitoAnnotations.openMocks(this);
//		repo = mock(IExamenRepository.class);
//		preguntaRepository = mock(IPreguntaRepository.class);
//		service = new ExamenService(repo, preguntaRepository);
	}*/
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
	@Test
	void testPreguntaExamenVerify() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
		.thenReturn(Datos.PREGUNTAS);
		
		Examen examen = service.findExamenPorNombreConPreguntas("Historia");
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("Aritmetica"));
//		Verifica que pase por los metodos asignados
		verify(repo).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(7L);
	}
	
	@Test
	void testNoExisteExamenVerify() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
		.thenReturn(Datos.PREGUNTAS);
		
		Examen examen = service.findExamenPorNombreConPreguntas("Historia");
		assertNotNull(examen);
		verify(repo).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(7L);
	}
	
	@Test
	void testGuardarExamen() {
//		GIVEN
		Examen newExamen = Datos.EXAMEN;
		newExamen.setPreguntas(Datos.PREGUNTAS);
		when(repo.guardar(any(Examen.class))).then(new Answer<Examen>() {
			Long secuencia = 8L;
			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {
				Examen examen = invocation.getArgument(0);
				examen.setId(secuencia++);
				return examen;
			}
			
		});
		
//		WHEN
		Examen examen = service.guardar(newExamen);
		
//		THEN
		assertNotNull(examen.getId());
		assertEquals(8L, examen.getId());
		assertEquals("Fisica", examen.getNombre());
		
		verify(repo).guardar(any(Examen.class));
		verify(preguntaRepository).guardarVarias(anyList());
	}
}
