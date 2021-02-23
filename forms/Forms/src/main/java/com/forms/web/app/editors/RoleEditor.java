package com.forms.web.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.web.app.services.RoleService;

@Component
public class RoleEditor extends PropertyEditorSupport{

	@Autowired
	private RoleService service;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(text);
			setValue(service.getRoleById(id));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
