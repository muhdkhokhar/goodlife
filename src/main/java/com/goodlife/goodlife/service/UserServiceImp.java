package com.goodlife.goodlife.service;

import java.util.Arrays;
import java.util.HashSet;

import com.goodlife.goodlife.Model.Role;
import com.goodlife.goodlife.Model.User;
import com.goodlife.goodlife.repository.RoleRepository;
import com.goodlife.goodlife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public void saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setStatus("VERIFIED");
		Role userRole = roleRepository.findByRole("SITE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public boolean isUserAlreadyPresent(User user) {
		// Try to implement this method, as assignment.
		return false;
	}

	@Override
	public void loadUser (String email)  {
		User user = userRepository.findAllByEmail(email);

//		if (user != null) {
//			return (UserDetails) user;
//		}
//		throw new UsernameNotFoundException(
//				"User '" + email + "' not found");
	}

}
