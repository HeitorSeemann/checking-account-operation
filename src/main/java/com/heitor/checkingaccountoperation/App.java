package com.heitor.checkingaccountoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.context.annotation.ComponentScan;

@EnableKafka
@SpringBootApplication
@ComponentScan(basePackages = "com.heitor.checkingaccountoperation")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
