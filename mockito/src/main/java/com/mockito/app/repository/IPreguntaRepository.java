package com.mockito.app.repository;

import java.util.List;

public interface IPreguntaRepository {

	List<String> findPreguntasPorExamenId(Long id);
}
