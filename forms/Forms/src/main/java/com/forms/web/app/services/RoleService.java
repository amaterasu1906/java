package com.forms.web.app.services;

import java.util.List;

import com.forms.web.app.models.Role;

public interface RoleService {

	public List<Role> listaRoles();
	public Role getRoleById(Integer id);
}
