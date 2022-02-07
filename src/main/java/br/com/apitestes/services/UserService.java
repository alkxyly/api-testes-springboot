package br.com.apitestes.services;

import br.com.apitestes.domain.User;

public interface UserService {
	User findById(Integer id);
}
