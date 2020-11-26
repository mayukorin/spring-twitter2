package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.component.DramaComponent;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsImpl;

public class NotCreateBlanktValidator implements ConstraintValidator<NotCreateBlank,String> {


	private final UserRepository userRepository;

	@Autowired
	DramaComponent targetDramaComponent;


	public NotCreateBlanktValidator() {
		this.userRepository = null;
	}

	@Autowired
	public NotCreateBlanktValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {

		UserDetailsImpl loginUser = null;
		try {

			loginUser = (UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
			return true;
		} catch(ClassCastException e) {
			System.out.println("新規会員登録の時");
			return userRepository == null || (!value.equals("")&& value != null);
		}




	}


}
