package com.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<String> nombres = Flux.just("Kokomi","Wendy","Haruhi")
				.doOnNext( elemento -> System.out.println(elemento));
		nombres.subscribe(logger::info);
		
	}

}