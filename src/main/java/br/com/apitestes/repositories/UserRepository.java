package br.com.apitestes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apitestes.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
