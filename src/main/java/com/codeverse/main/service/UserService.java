package com.codeverse.main.service;

import com.codeverse.main.entities.User;

public interface UserService {

	public void registerUser(User user);

	public boolean loginUser(String email, String password);

}
