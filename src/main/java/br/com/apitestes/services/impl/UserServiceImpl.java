package br.com.apitestes.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;
import br.com.apitestes.repositories.UserRepository;
import br.com.apitestes.services.UserService;
import br.com.apitestes.services.exceptions.DataIntegratyViolationException;
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
		findByEmail(userDTO);
		return userRepository.save(user);
	}
	
	@Override
	public User update(UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		findByEmail(userDTO);
		return userRepository.save(user);
	}
	

	@Override
	public void delete(Integer id) {
		findById(id);
		userRepository.deleteById(id);
	}
	
	private void findByEmail(UserDTO userDTO){
		Optional<User> userOp = userRepository.findByEmail(userDTO.getEmail());
		
		userOp.ifPresent( user -> {
			if(!user.getId().equals(userDTO.getId()))
				throw new  DataIntegratyViolationException(String.format(
					"Email %s já cadastrado", userDTO.getEmail()));
		});
	}


}

