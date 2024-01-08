package com.MyRH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MyRHApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRHApplication.class, args);
	}

}
