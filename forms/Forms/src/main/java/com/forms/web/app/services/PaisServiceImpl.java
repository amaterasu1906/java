package com.forms.web.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forms.web.app.models.Pais;

@Service
public class PaisServiceImpl implements PaisService {

	public List<Pais> lista;
	public PaisServiceImpl() {
		this.lista = Arrays.asList( 
				new Pais(1,"ESP", "España"),
				new Pais(2,"MEX", "México"),
				new Pais(3,"HON", "Honduras"),
				new Pais(4,"EUA", "Estados Unidos"),
				new Pais(5,"CAN", "Cánada"));
	}

	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais buscarPaisById(Integer id) {
		for (Pais pais : lista) {
			if(pais.getId().equals(id)) {
				return pais;
			}
		}
		return null;
	}

}
