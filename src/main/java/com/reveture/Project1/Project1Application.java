package com.reveture.Project1;

import com.reveture.Project1.entity.Account;
import com.reveture.Project1.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Project1Application implements CommandLineRunner {

	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando aplicacion");
	}

}
