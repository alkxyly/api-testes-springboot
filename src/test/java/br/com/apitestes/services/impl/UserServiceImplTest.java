package br.com.apitestes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import br.com.apitestes.services.exceptions.ObjectNotFoundException;

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

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

		try {
			service.findById(ID);
		}catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(e.getMessage(), "Objeto não encontrado");
		}
	}
}
