package br.com.apitestes.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.services.impl.UserServiceImpl;

@SpringBootTest
class UserResourceTest {

	private static final int INDEX = 0;
	private static final int ID          = 1;
	private static final String NAME     = "jose";
	private static final String PASSWORD = "123456";
	private static final String EMAIL    = "jose@gmail.com";
	private static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";

	
	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;

	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(1, NAME, EMAIL, PASSWORD);
	}
	
	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = resource.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(PASSWORD, response.getBody().getPassword());
	}
	
	@Test
	void whenFindAllThenReturnSuccess() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
		
		assertEquals(ID, response.getBody().get(INDEX).getId());
		assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
		assertEquals(NAME, response.getBody().get(INDEX).getName());
		assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
	}
	
	@Test
	void whenCreateThenReturnCreated() {
		when(service.create(any())).thenReturn(user);
		
		ResponseEntity<UserDTO> response = resource.create(userDTO);
		
		assertNotNull(response);
		assertNull(response.getBody());
		assertNotNull(response.getHeaders().getLocation());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(service.update(any())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = resource.update(ID, userDTO);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(NAME, response.getBody().getName());
	}

}
