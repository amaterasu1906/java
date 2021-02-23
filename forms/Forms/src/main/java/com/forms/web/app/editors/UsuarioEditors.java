package com.forms.web.app.editors;

import java.beans.PropertyEditorSupport;

public class UsuarioEditors extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text.toUpperCase().trim());
	}

}
