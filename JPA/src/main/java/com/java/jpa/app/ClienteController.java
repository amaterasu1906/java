package com.java.jpa.app;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.services.IClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String getAllCliente(Model model) {
		model.addAttribute("titulo", "Mostrando la lista de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario del cliente");
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name = "id") Long id, Model model) {
		Cliente cliente = null;
		if(id>0) {
			cliente = clienteService.findById(id);
		}else {
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario del cliente");
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, Model model) {
		Cliente cliente = null;
		if(id>0) {
			clienteService.deleteCliente(id);
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario del cliente");
		return "redirect:/listar";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model) {
		if( result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del cliente");
			return "form";
		}
		clienteService.save(cliente);
		return "redirect:/listar";
	}
}
