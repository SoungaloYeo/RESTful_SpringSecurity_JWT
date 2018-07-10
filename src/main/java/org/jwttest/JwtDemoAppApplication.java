package org.jwttest;

import java.util.stream.Stream;

import org.jwttest.domain.MyRole;
import org.jwttest.domain.MyUser;
import org.jwttest.domain.Task;
import org.jwttest.repository.TaskRepository;
import org.jwttest.secutity.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtDemoAppApplication implements CommandLineRunner{

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoAppApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }

	@Override
	public void run(String... args) throws Exception {

		accountService.saveUser(new MyUser(null, "admin", "1234", null));
		accountService.saveUser(new MyUser(null, "user", "1234", null));
		accountService.saveRole(new MyRole(null, "ADMIN"));
		accountService.saveRole(new MyRole(null, "USER"));
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.addRoleToUser("user", "USER");



		Stream.of("task 1", "task 2", "task 3", "task 4").forEach(t -> {
			taskRepository.save(new Task(null, t));
		});
		taskRepository.findAll().forEach(t -> {
			System.out.println(t.getTaskName());
		});
	}
}
