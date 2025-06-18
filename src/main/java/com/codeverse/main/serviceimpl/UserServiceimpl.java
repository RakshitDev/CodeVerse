package com.codeverse.main.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeverse.main.entities.User;
import com.codeverse.main.repository.UserRepository;
import com.codeverse.main.service.UserService;

@Service
public class UserServiceimpl  implements UserService{
	
	@Autowired
	public UserRepository userRepository;
	

	@Override
	public void registerUser(User user) {
		userRepository.save(user);
	
	}


	@Override
	public boolean loginUser(String email, String password) {
		User existingUser = userRepository.findByEmail(email);
		if(existingUser!=null)
		{
			return existingUser.getPassword().equals(password);
		}
		return false;
	}

}
