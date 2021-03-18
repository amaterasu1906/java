package com.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.webflux.app.models.dao.ProductoDao;
import com.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;


@Controller
public class ProductoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoDao dao;
	
	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
		Flux<Producto> productos = dao.findAll()
				.map( producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				});
		productos.subscribe( prod ->
				logger.info("->" + prod.getNombre())
				);
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		return "listar";
	}
	
	@GetMapping("/listar-full")
	public String listarFull(Model model) {
		Flux<Producto> productos = dao.findAll()
				.map( producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				}).repeat(5000);
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		return "listar";
	}
	@GetMapping("/listar-chunked")
	public String listarChunke(Model model) {
		Flux<Producto> productos = dao.findAll()
				.map( producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				}).repeat(5000);
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		return "listar-chunked";
	}
	
	@GetMapping("/listar-datadriver")
	public String listarDataDiver(Model model) {
		Flux<Producto> productos = dao.findAll()
				.map( producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.delayElements(Duration.ofSeconds(1));
		productos.subscribe( prod ->
		logger.info("->" + prod.getNombre())
				);
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
		return "listar";
	}
}
