package com.xpresspayments.airtime.vtu;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info =
		@Info(
				description = "API service that authenticates users and provide airtime payments, using Xpress Payments API.",
				title = "Airtime_VTU App",
				version = "1.0"
		),
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "DEV Server"
				)
		},
		security = {
				@SecurityRequirement(
						name = "Bearer Authentication")
		})
@SecurityScheme(
		name = "Bearer Authentication",
		description = "JWT Authentication",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class Airtime_VTU_Application {

	public static void main(String[] args) {
		SpringApplication.run(Airtime_VTU_Application.class, args);
	}

}
