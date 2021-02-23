package com.forms.web.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forms.web.app.models.Role;

@Service
public class RoleServiceImpl implements RoleService {
	
	private List<Role> listaRoles;
	public RoleServiceImpl() {
		this.listaRoles = new ArrayList<>();
		this.listaRoles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
		this.listaRoles.add(new Role(2, "Usuario", "ROLE_USER"));
		this.listaRoles.add(new Role(3, "Moderador", "ROLE_MODERATOR"));
		this.listaRoles.add(new Role(4, "Invitado", "ROLE_INVITADO"));
	}

	@Override
	public List<Role> listaRoles() {
		return this.listaRoles;
	}

	@Override
	public Role getRoleById(Integer id) {
		for (Role role : listaRoles) {
			if( role.getId().equals(id))
				return role;
		}
		return null;
	}

}
