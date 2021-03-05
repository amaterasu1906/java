package com.java.jpa.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.models.entity.Factura;
import com.java.jpa.app.services.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("facturas")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model,
			RedirectAttributes flash) {
		Cliente cliente = clienteService.findById(clienteId);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/listar";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "crear factura");
		
		return "factura/form";
	}
}
