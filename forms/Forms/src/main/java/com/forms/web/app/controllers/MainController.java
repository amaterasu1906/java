package com.forms.web.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.forms.web.app.editors.PaisEditors;
import com.forms.web.app.editors.RoleEditor;
import com.forms.web.app.editors.UsuarioEditors;
import com.forms.web.app.models.Pais;
import com.forms.web.app.models.Role;
import com.forms.web.app.models.Usuario;
import com.forms.web.app.services.PaisService;
import com.forms.web.app.services.RoleService;
import com.forms.web.app.validators.ValidatorUsuario;

@Controller
@SessionAttributes("usuario")
public class MainController {

	@Autowired
	private ValidatorUsuario validador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PaisEditors paisEditor;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleEditor roleEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, false));
//		binder.registerCustomEditor(String.class, new UsuarioEditors());
		binder.registerCustomEditor(String.class, "nombre", new UsuarioEditors());
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Role.class, "roles", roleEditor);
	}
	
	@ModelAttribute("generos")
	public List<String> generos(){
		return Arrays.asList("Hombre", "Mujer");
	}
	@ModelAttribute("rolesObject")
	public List<Role> rolesObject(){
		return roleService.listaRoles();
	}
	
	@ModelAttribute("rolesString")
	public List<String> rolesString(){
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");
		roles.add("USER");
		roles.add("MODERADOR");
		roles.add("INVITADO");
		return roles;
	}
	
	@ModelAttribute("paises")
	public List<String> paises(){
		return Arrays.asList("España", "México", "Honduras", "Estados Unidos", "Cánada");
	}
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		return paisService.listar();
	}
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap(){
		Map<String, String> paises = new HashMap<>();
		paises.put("ESP", "España");
		paises.put("MEX", "México");
		paises.put("HON", "Honduras");
		paises.put("EUA", "Estados Unidos");
		paises.put("CAN", "Cánada");
		return paises; 
	}
	
	@GetMapping("/form")
	public String home(Model model) {
		Usuario user = new Usuario();
		user.setNombre("Kokomi");
		user.setApellido("Teruhashi");
		user.setId("00.000.000-X");
		user.setCuenta(20);
		user.setEmail("nashi@gmail.com");
		user.setFechaNacimiento(new GregorianCalendar(2021,2,10).getTime());
		user.setPais(new Pais(3, "HON", "Honduras"));
		user.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
		user.setHabilitado(true);
		model.addAttribute("titulo", "Formularios");
//		model.addAttribute("usuario", new Usuario());
		model.addAttribute("usuario", user);
		return "form";
	}
	
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
//		validador.validate(usuario, result);
		if( result.hasErrors()) {
			model.addAttribute("titulo", "Formularios");
//			Map<String, String> errores = new HashMap<>();
//			result.getFieldErrors().forEach( error ->{
//				errores.put(error.getField(), "El campo : ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()));
//			});
//			model.addAttribute("error", errores);
			return "form";
		}
//		model.addAttribute("usuario", usuario);
		
		return "redirect:/ver";
	}
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus estatus) {
		if( usuario == null ) {
			return "redirect:/form";
		}
		model.addAttribute("titulo", "Formularios");
		estatus.setComplete();
		return "resultado";
	}
}
