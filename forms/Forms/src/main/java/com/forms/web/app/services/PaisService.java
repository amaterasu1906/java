package com.forms.web.app.services;

import java.util.List;

import com.forms.web.app.models.Pais;

public interface PaisService {
	public List<Pais> listar();
	public Pais buscarPaisById(Integer id);

}
