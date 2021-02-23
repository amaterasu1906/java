package com.horario.web.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	@Value("${config.horario.apertura}")
	private Integer apertura;
	@Value("${config.horario.cierre}")
	private Integer cierre;
	
	@GetMapping({"/", "index"})
	public String index(Model model) {
		model.addAttribute("titulo", "Bienvenido al horario de atención a clientes");
		return "index";
	}
	
	@GetMapping("/cerrado")
	public String cerrado(Model model) {
		StringBuilder mensaje = new StringBuilder("Cerrado. Por favor visitenos en un horario de ");
		mensaje.append(apertura);
		mensaje.append(" hasta ");
		mensaje.append(cierre);
		mensaje.append(" hrs. Muchas gracias.");
		
		model.addAttribute("titulo", "Fuera del horario de servicio");
		model.addAttribute("mensaje", mensaje.toString());
		
		return "cerrado";
	}
}
