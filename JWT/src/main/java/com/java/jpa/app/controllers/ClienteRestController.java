package com.java.jpa.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jpa.app.services.IClienteService;
import com.java.jpa.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	@Secured("ROLE_USER")
	public ClienteList listar(){
//		Primera forma retornando un List<Cliente>, retornar del mismo tipo
//		return clienteService.findAll();
//		Segunda forma, retornando un wrapper, para obtener un json y un XML
		return new ClienteList(clienteService.findAll());
	}
}
