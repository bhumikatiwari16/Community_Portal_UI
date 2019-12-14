package com.mobiletaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.mobiletaas.controllers.RunRetrieveController;

@SpringBootApplication(scanBasePackages = {"com.mobiletaas"})
@ComponentScan(basePackageClasses=RunRetrieveController.class)
public class Cmpe281MobileTaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cmpe281MobileTaasApplication.class, args);
	}

}
