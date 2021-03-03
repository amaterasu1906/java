package com.java.jpa.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.services.IClienteService;
import com.java.jpa.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String getAllCliente(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 20);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Mostrando la lista de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
//		model.addAttribute("clientes", clienteService.findAll());
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
	public String editar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = null;
		if(id>0) {
			cliente = clienteService.findById(id);
			if( cliente == null ) {
				flash.addFlashAttribute("error", "Cliente no encontrado.");
				return "redirect:/listar";				
			}
			flash.addFlashAttribute("success", "Cliente editado exitosamente.");
		}else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser 0.");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario del cliente");
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
		if(id>0) {
			clienteService.deleteCliente(id);
			flash.addFlashAttribute("success", "Cliente eliminado exitosamente.");
		}
		return "redirect:/listar";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, @RequestParam("file") MultipartFile foto, Model model, RedirectAttributes flash, SessionStatus status) {
		if( result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del cliente");
			return "form";
		}
		if( !foto.isEmpty()) {
			Path ruta = Paths.get("src//main//resources//static//uploads");
			String rutaAbsoluta = ruta.toFile().getAbsolutePath();
			try {
				byte[] bytes = foto.getBytes();
				Path rutaFinal = Paths.get(rutaAbsoluta+"//"+foto.getOriginalFilename());
				Files.write(rutaFinal, bytes);
				flash.addFlashAttribute("info", "Se ha subido correctamente la imagen");
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado exitosamente." : "Cliente creado exitosamente.";
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable(name = "id") Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = clienteService.findById(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no se encontro");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: ".concat(cliente.getNombre()));
		return "ver";
	}
}
