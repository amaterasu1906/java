package com.errores.web.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.errores.web.app.models.Usuario;
import com.errores.web.app.services.UsuarioService;

@Controller
public class AppController {
	
	@Autowired
	private UsuarioService service;
	
	@SuppressWarnings("unused")
	@GetMapping("/index")
	public String index(Model model) {
//		Integer x = 100/0;
//		Integer x = Integer.parseInt("10x");
		return "index";
	}
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
		Usuario user = service.getUsuarioById(id);
		model.addAttribute("usuario", user);
		model.addAttribute("titulo", "Detalle de usuario: ".concat(user.getNombre()));
		return "ver";
	}
}
