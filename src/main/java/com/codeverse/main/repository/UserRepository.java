package com.codeverse.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeverse.main.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
		
	 public User findByEmail(String email);
}
