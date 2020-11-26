package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteUserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public void insert(SiteUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);
	}

	public void update(SiteUser user,String newPassword,String originPassword) {

		if (newPassword.equals("") || newPassword == null) {

			user.setPassword(originPassword);
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}


		userRepository.save(user);
	}

	public SiteUser findUserById(Long id) {

		return userRepository.findById(id).get();

	}

}
