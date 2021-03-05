package com.java.jpa.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.services.IClienteService;

@Controller
@RequestMapping(name = "/factura/**")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
//	@GetMapping("/form/{clienteId}")
//	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model,
//			RedirectAttributes flash) {
//		Cliente cliente = clienteService.findById(clienteId);
//		
//		return "factura/form";
//	}
}
