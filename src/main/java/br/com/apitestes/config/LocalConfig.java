package br.com.apitestes.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.apitestes.domain.User;
import br.com.apitestes.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private UserRepository userRepository;

	@Bean
	public void startDB() {
		User user = new User(null, "José", "jose@gmail.com", "123456");
		User user2 = new User(null, "José", "jose2@gmail.com", "123456");
		userRepository.saveAll(List.of(user, user2));
	}
}
