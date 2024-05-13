package me.maodud.vault;

import me.maodud.vault.annotation.service.MethodCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaultApplication implements CommandLineRunner {

	@Autowired
	private MethodCallService methodCallService;

	@Override
	public void run(String... args) throws Exception {
		methodCallService.print100();
	}

	public static void main(String[] args) {
		SpringApplication.run(VaultApplication.class, args);
	}
}
