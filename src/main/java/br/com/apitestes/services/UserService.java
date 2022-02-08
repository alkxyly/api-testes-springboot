package br.com.apitestes.services;

import java.util.List;

import br.com.apitestes.domain.User;
import br.com.apitestes.domain.dto.UserDTO;

public interface UserService {
	User findById(Integer id);
	List<User> findAll();
	User create(UserDTO userDTO);
	User update(UserDTO userDTO);
}
