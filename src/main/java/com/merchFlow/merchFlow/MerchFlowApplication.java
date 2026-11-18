package com.merchFlow.merchFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.merchflow")
@EntityScan(basePackages = "com.merchflow")
@EnableJpaRepositories(basePackages = "com.merchflow")
public class MerchFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchFlowApplication.class, args);
	}

}
