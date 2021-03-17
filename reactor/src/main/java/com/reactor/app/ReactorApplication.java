package com.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Usuario> nombres = Flux.just("Kokomi","Wendy","Haruhi")
				.map( nombre -> new Usuario(nombre, null))
				.filter(usuario -> usuario.getNombre().equalsIgnoreCase("kokomi"))
				.doOnNext( elemento -> System.out.println(elemento))
				.map( user -> {
					user.setNombre(user.getNombre().toUpperCase());
					 return user;
				});
		nombres.subscribe( user ->
				logger.info(user.getNombre()),
				error -> logger.error(error.getMessage()),
				new Runnable() {
					
					@Override
					public void run() {
						logger.info("Completo");
						
					}
				}
		);
		
	}

}