package com.java.jpa.app.view.xml;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.java.jpa.app.models.entity.Cliente;
import com.java.jpa.app.services.IClienteService;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView{

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		model.remove("titulo");
		model.remove("page");
//		Para mostrar solo los de la pagina
//		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		List<Cliente> clientes = clienteService.findAll();
		model.remove("clientes");
//		model.put("clientesList", new ClienteList( clientes.getContent()));
		model.put("clientesList", new ClienteList( clientes));
		super.renderMergedOutputModel(model, request, response);
	}

	
}
