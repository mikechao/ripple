package com.mike.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mike.User;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private User currentUser;
	
	private Set<User> users = new HashSet<>();

	@Override
	public boolean userExists(User user) {
		return users.contains(user);
	}

	@Override
	public void setCurrentUser(User user) {
		logger.info("Set current user to {}", user);
		this.currentUser = user;
		users.add(currentUser);
	}
	
	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public void addRemoteUser(User user) {
		if (!currentUser.equals(user)) {
			if (!users.contains(user)) {
				users.add(user);
				logger.info("Added remote user {}", user);
			}
		}
		
	}

}
