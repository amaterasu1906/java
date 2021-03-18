package com.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.webflux.app.models.dao.ProductoDao;
import com.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner{

	@Autowired
	private ProductoDao dao;

	private static final Logger logger = LoggerFactory.getLogger(WebfluxApplication.class);
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(
				new Producto("Sony pantalla", 11000.00),
				new Producto("HP pantalla", 3600.00),
				new Producto("TCL pantalla", 6000.00)
				)
		.flatMap( producto -> {
			producto.setCreateAt(new Date());
			return dao.save(producto);
		})
		.subscribe( producto -> logger.info( "insert : " + producto.getId() + " - " + producto.getNombre()));
	}

}
