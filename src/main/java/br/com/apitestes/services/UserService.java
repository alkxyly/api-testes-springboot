package br.com.apitestes.services;

import java.util.List;

import br.com.apitestes.domain.User;

public interface UserService {
	User findById(Integer id);
	List<User> findAll();
}
