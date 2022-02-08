package br.com.apitestes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apitestes.domain.User;
import br.com.apitestes.repositories.UserRepository;
import br.com.apitestes.services.UserService;
import br.com.apitestes.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findById(Integer id) {
		Optional<User> userOp = userRepository.findById(id);
		return userOp.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
