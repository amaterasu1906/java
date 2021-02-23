package com.errores.web.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.errores.web.app.models.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> lista;
	
	public UsuarioServiceImpl() {
		this.lista = new ArrayList<>();
		this.lista.add(new Usuario(1, "Kokomi", "Teruhashi"));
		this.lista.add(new Usuario(2, "Kusou", "Saiki"));
		this.lista.add(new Usuario(3, "Rikki", "Nendo"));
		this.lista.add(new Usuario(4, "Shun", "Kaido"));
		this.lista.add(new Usuario(5, "Chisato", "Mera"));
	}

	@Override
	public List<Usuario> listar() {
		return this.lista;
	}

	@Override
	public Usuario getUsuarioById(Integer id) {
		return lista.stream().filter(e->e.getId().equals(id)).findFirst().orElse(null);
	}

}
