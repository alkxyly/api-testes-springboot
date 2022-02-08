package br.com.apitestes.services.impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.repositories.UserRepository;

@SpringBootTest
class UserServiceImplTest {

	private static final String PASSWORD = "123456";
	private static final String EMAIL    = "jose@gmail.com";
	private static final String NAME     = "jose";
	private static final int ID          = 1;

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



}
