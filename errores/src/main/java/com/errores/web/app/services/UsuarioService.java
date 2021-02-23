package com.errores.web.app.services;

import java.util.List;

import com.errores.web.app.models.Usuario;

public interface UsuarioService {

	public List<Usuario> listar();
	public Usuario getUsuarioById(Integer id);
	
}
