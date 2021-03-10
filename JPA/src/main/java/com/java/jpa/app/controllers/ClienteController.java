package com.java.jpa.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import com.java.jpa.app.services.IUploadService;
import com.java.jpa.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IClienteService clienteService;
	@Autowired
	private IUploadService uploadService;
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping({"/listar", "/"})
	public String getAllCliente(@RequestParam(name = "page", defaultValue = "0") int page, 
			Model model, Authentication authentication, HttpServletRequest request, Locale locale) {
		
		if( authentication != null ) {
			logger.info("Username: ".concat(authentication.getName()).concat(" ha iniciado sesion"));
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth != null ) {
			logger.info("[SecurityContextHolder]Username: ".concat(auth.getName()).concat(" ha iniciado sesion"));
			if( hasRole("ROLE_ADMIN")) {
				logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso."));
			}else {
				logger.info("Hola ".concat(auth.getName()).concat(" no tienes acceso."));
				
			}
//			Segunda forma con SecurityContextHolderAwareRequestWrapper para validar un Role
			SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
			if( securityContext.isUserInRole("ADMIN")) {
				logger.info("[SecurityContextHolderAwareRequestWrapper]Hola ".concat(auth.getName()).concat(" tienes acceso."));
			}else {
				logger.info("[SecurityContextHolderAwareRequestWrapper]Hola ".concat(auth.getName()).concat(" no tienes acceso."));
				
			}
			
//			Tercera forma con HttpServletRequest se requiere el nombre completo
			if( request.isUserInRole("ROLE_ADMIN")) {
				logger.info("[HttpServletRequest]Hola ".concat(auth.getName()).concat(" tienes acceso."));
			}else {
				logger.info("[HttpServletRequest]Hola ".concat(auth.getName()).concat(" no tienes acceso."));
				
			}
		}
		
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
//		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario del cliente");
		return "form";
	}
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
		if(id>0) {
			Cliente cliente = clienteService.findById(id);
			clienteService.deleteCliente(id);
			flash.addFlashAttribute("success", "Cliente eliminado exitosamente.");
			if( uploadService.delete(cliente.getFoto()) )
				flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + " ha sido eliminado con exito.");
		}
		return "redirect:/listar";
	}
	
	@Secured("ROLE_USER")
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		Resource recurso = null;
		try {
			recurso = uploadService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, @RequestParam("file") MultipartFile foto, Model model, RedirectAttributes flash, SessionStatus status) {
		if( result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del cliente");
			return "form";
		}
		if( !foto.isEmpty()) {
//			Path ruta = Paths.get("C://Temp//uploads");
//			String rutaAbsoluta = ruta.toFile().getAbsolutePath();
			
			if( cliente.getId() != null
				&& cliente.getId() > 0
				&& cliente.getFoto() != null
				&& cliente.getFoto().length() > 0 ){
					if(uploadService.delete(cliente.getFoto()))
						flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + " ha sido eliminado con exito.");
				}
			String uniqueFileName = null;
			try {
				uniqueFileName = uploadService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Se ha subido correctamente la imagen");
			cliente.setFoto(uniqueFileName);
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado exitosamente." : "Cliente creado exitosamente.";
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable(name = "id") Long id, RedirectAttributes flash, Model model) {
//		Cliente cliente = clienteService.findById(id);
		Cliente cliente = clienteService.findClienteByIdWithFacturas(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no se encontro");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: ".concat(cliente.getNombre()));
		return "ver";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if( context == null ) {
			return false;
		}
		Authentication auth = context.getAuthentication();
		if( auth == null ) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//		for (GrantedAuthority authority : authorities) {
//			if( role.equals(authority.getAuthority())) {
//				logger.info("Hola usuario".concat(auth.getName()).concat(" tu rol es: ").concat(authority.getAuthority()));
//				return true;
//			}
//		}
//		return false;
		return authorities.contains(new SimpleGrantedAuthority(role));
	}
}
