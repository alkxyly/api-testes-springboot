package br.com.apitestes.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.services.UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		return ResponseEntity.ok(userService.findAll()
				.stream()
				.map(user -> mapper.map(user, UserDTO.class))
				.collect(Collectors.toList()));
	}
}
