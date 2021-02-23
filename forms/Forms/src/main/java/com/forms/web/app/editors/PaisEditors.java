package com.forms.web.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.web.app.services.PaisService;

@Component
public class PaisEditors extends PropertyEditorSupport{

	@Autowired
	private PaisService service;
	
	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(idString);
			this.setValue(service.buscarPaisById(id));
		} catch (NumberFormatException e) {
			this.setValue(null);
		}
	}

}
