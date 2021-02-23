package com.forms.web.app.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.forms.web.app.models.Usuario;

@Component
public class ValidatorUsuario implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		Usuario user = (Usuario)target;
		
		ValidationUtils.rejectIfEmpty(errors, "apellido", "NotEmpty.usuario.apellido");
		
//		if(!user.getId().matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
//			errors.rejectValue("id", "Pattern.usuario.id");
//		}

	}

}
