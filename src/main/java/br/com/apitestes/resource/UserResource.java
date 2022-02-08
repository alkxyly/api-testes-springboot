package br.com.apitestes.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.services.UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
	
	private static final String ID = "/{id}";

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(ID)
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
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
		
		User user = userService.create(userDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path(ID)
				.buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(ID)
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO){
		userDTO.setId(id);
		User user = userService.update(userDTO);
		return ResponseEntity.ok(mapper.map(user, UserDTO.class));
	}
	
	@DeleteMapping(ID)
	public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
