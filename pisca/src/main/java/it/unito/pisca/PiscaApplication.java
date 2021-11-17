package it.unito.pisca;

import it.unito.pisca.entity.Category;
import it.unito.pisca.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PiscaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiscaApplication.class, args);
	}


	/*
	@Bean
	public CommandLineRunner demoData(CategoryRepository repo) {
		return args -> {
			repo.save(new Category("Dipinti","Raccolta dei dipinti"));
			repo.save(new Category("Scultura","Raccolta delle sculture"));
		};
	}*/

}
