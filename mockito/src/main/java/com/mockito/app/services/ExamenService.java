package com.mockito.app.services;

import java.util.List;
import java.util.Optional;

import com.mockito.app.models.Examen;
import com.mockito.app.repository.IExamenRepository;
import com.mockito.app.repository.IPreguntaRepository;

public class ExamenService implements IExamenService{

	private IExamenRepository examenRepository;
	private IPreguntaRepository preguntaRepository;
	
	public ExamenService(IExamenRepository examenRepository, IPreguntaRepository preguntaRepository) {
		this.examenRepository = examenRepository;
		this.preguntaRepository = preguntaRepository;
	}

	@Override
	public Optional<Examen> findExamenPorNombre(String nombre) {
		return examenRepository.findAll().stream()
				.filter(e -> e.getNombre().equals(nombre))
				.findFirst();
	}

	@Override
	public Examen findExamenPorNombreConPreguntas(String nombre) {
		Optional<Examen> examenOptiona = findExamenPorNombre(nombre);
		Examen examen = null;
		if( examenOptiona.isPresent()) {
			examen = examenOptiona.orElseThrow();
			List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}
		return examen;
	}

	@Override
	public Examen guardar(Examen examen) {
		if( !examen.getPreguntas().isEmpty()) {
			preguntaRepository.guardarVarias(examen.getPreguntas());
		}
		return examenRepository.guardar(examen);
	}

}
