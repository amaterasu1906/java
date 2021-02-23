package com.forms.web.app.validators.anotaciones;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidacionIdImpl implements ConstraintValidator<ValidacionId, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}");
	}

}
