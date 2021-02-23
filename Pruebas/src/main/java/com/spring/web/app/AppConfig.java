package com.spring.web.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.web.app.factura.ItemFactura;
import com.spring.web.app.factura.Producto;

@Configuration
public class AppConfig {

	@Bean("itemsFactura")
	public List<ItemFactura> setItems(){
		Producto p1 = new Producto("Lentes",100);
		Producto p2 = new Producto("Bocina",1000);
		
		ItemFactura i1 = new ItemFactura(p1,2);
		ItemFactura i2 = new ItemFactura(p2,4);
		
		return Arrays.asList(i1,i2);
	}
	@Bean("default")
	public List<ItemFactura> setItems2(){
		Producto p1 = new Producto("Mouse",400);
		Producto p2 = new Producto("Regulador",230);
		Producto p3 = new Producto("Teclado Gammer",1230);
		
		ItemFactura i1 = new ItemFactura(p1,2);
		ItemFactura i2 = new ItemFactura(p2,4);
		ItemFactura i3 = new ItemFactura(p3,2);
		
		return Arrays.asList(i1,i2, i3);
	}
}
