package br.com.system.carrental;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@OpenAPIDefinition(
		info = @Info(
				title = "System CarRental - API Documentation",
				description = "This is an api to a car rental system",
				contact = @Contact(name = "Kawe M. Carvalho", url = "https://github.com/kawemorais/car-rental-system"),
				version = "1.0"
		)
)
public class CarrentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrentalApplication.class, args);
	}

}
