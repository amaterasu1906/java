package com.spring.web.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.web.app.factura.Factura;

@Controller
@RequestMapping("/app")
public class MainController {

	@Autowired
	private Factura factura;
	
	@GetMapping({"/","", "index"})
	public String home(Model model) {
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "App Spring");
		return "inicio";
	}
}
