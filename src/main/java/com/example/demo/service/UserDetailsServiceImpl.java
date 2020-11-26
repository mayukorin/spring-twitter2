package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		SiteUser user = userRepository.findByName(name);

		if (user == null) {
			throw new UsernameNotFoundException(name+"not found");
		}

		return createUserDetails(user);

	}

	public User createUserDetails(SiteUser user) {

		Set<GrantedAuthority> grantedAuthories = new HashSet<>();

		grantedAuthories.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new UserDetailsImpl(user,grantedAuthories);
	}

}
