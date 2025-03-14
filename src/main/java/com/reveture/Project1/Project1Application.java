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
		System.out.println("Testing database connection...");


		long count = accountRepository.count();
		System.out.println("Total accounts in DB: " + count);

		List<Account> accounts = accountRepository.findAll();
		System.out.println("Accounts in DB:");

		for (Account account : accounts) {
			System.out.println("ID: " + account.getId() + ", Email: " + account.getEmail());
		}
	}

}
