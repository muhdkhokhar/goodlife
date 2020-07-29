package com.goodlife.goodlife.service;

import com.goodlife.goodlife.Model.User;

public interface UserService {

	public void saveUser(User user);
	
	public boolean isUserAlreadyPresent(User user);

	public void loadUser(String email);
}
