package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsImpl;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin,String> {

	private final UserRepository userRepository;

	public UniqueLoginValidator() {
		this.userRepository = null;
	}

	@Autowired
	public UniqueLoginValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {

		UserDetailsImpl loginUser = null;
		try {

			loginUser = (UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
			return userRepository == null || userRepository.findByName(value) == null||loginUser.getUsername().equals(value);
		} catch(ClassCastException e) {
			System.out.println("新規会員登録の時");
			return userRepository == null || userRepository.findByName(value) == null;
		}


	}



}
