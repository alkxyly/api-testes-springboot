package br.com.apitestes.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apitestes.domain.User;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(new User(1, "José da Silva", "jose@gmail.com", "123456"));
	}
}
