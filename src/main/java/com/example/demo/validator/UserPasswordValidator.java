package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsImpl;

public class UserPasswordValidator implements ConstraintValidator<UserPassword,String> {

	private final UserRepository userRepository;

	public UserPasswordValidator() {
		this.userRepository = null;
	}

	@Autowired
	public UserPasswordValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {


		try {

			UserDetailsImpl loginUser = (UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

			if (value == null || value.equals("")) {

			}
			return true;
		} catch(ClassCastException e) {

			System.out.println("新規会員登録の時");
			return (value!= null && !value.equals("")) ||userRepository == null ; 
		}
	}

}
