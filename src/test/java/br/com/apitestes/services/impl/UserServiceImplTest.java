package br.com.apitestes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.repositories.UserRepository;
import br.com.apitestes.services.exceptions.DataIntegratyViolationException;
import br.com.apitestes.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {

	private static final int INDEX = 0;
	private static final int ID          = 1;
	private static final String NAME     = "jose";
	private static final String PASSWORD = "123456";
	private static final String EMAIL    = "jose@gmail.com";
	private static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ModelMapper mapper;

	private User user;

	private UserDTO userDTO;

	private Optional<User> userOptional;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(1, NAME, EMAIL, PASSWORD);
		userOptional = Optional.of(new User(1, NAME, EMAIL, PASSWORD));
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(userRepository.findById(anyInt())).thenReturn(userOptional);

		User response = service.findById(ID);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NÃO_ENCONTRADO));

		try {
			service.findById(ID);
		}catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(e.getMessage(), OBJETO_NÃO_ENCONTRADO);
		}
	}
	
	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user));
		List<User> users = service.findAll();
		
		assertNotNull(users);
		assertEquals(1, users.size());
		assertEquals(User.class, users.get(INDEX).getClass());
		
		assertEquals(ID, users.get(INDEX).getId());
		assertEquals(NAME, users.get(INDEX).getName());
		assertEquals(EMAIL, users.get(INDEX).getEmail());
		assertEquals(PASSWORD, users.get(INDEX).getPassword());
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(userRepository.save(any())).thenReturn(user);
		
		User user = service.create(userDTO);
		
		assertNotNull(user);
		assertEquals(User.class, user.getClass());
		assertEquals(ID, user.getId());
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PASSWORD, user.getPassword());
	}
	
	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userOptional);
		
		try {
			userOptional.get().setId(2);
			service.create(userDTO);
		}catch (Exception e) {
			assertEquals(DataIntegratyViolationException.class, e.getClass());
			assertEquals("Email "+EMAIL+" já cadastrado", e.getMessage());
		}
	}
}
