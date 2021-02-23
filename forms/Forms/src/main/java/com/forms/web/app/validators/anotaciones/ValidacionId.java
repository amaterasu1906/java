package com.forms.web.app.validators.anotaciones;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidacionIdImpl.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidacionId {

	String message() default "{javax.validation.constraints.NotEmpty.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
