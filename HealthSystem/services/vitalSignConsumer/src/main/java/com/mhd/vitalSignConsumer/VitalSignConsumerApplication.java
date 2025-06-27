package com.mhd.vitalSignConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VitalSignConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitalSignConsumerApplication.class, args);
	}

}
