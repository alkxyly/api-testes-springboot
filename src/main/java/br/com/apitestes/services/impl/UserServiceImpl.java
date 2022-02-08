package br.com.apitestes.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.repositories.UserRepository;
import br.com.apitestes.services.UserService;
import br.com.apitestes.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		Optional<User> userOp = userRepository.findById(id);
		return userOp.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		return userRepository.save(user);
	}
	
	

}
