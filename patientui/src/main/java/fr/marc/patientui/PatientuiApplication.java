package fr.marc.patientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("fr.marc.patientui")
@SpringBootApplication
public class PatientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientuiApplication.class, args);
	}

}
