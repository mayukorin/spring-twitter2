package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.component.DramaComponent;
import com.example.demo.repository.DramaRepository;

public class UniqueDramaValidator implements ConstraintValidator<UniqueDrama,String>  {


	private final DramaRepository DramaRepository;

	@Autowired
	DramaComponent targetDramaComponent;


	public UniqueDramaValidator() {
		this.DramaRepository = null;
	}

	@Autowired
	public UniqueDramaValidator(DramaRepository dramaRepository) {
		this.DramaRepository = dramaRepository;
	}

	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {


		if (DramaRepository != null && DramaRepository.searchDramaByName(value) != null && (targetDramaComponent.getDrama() == null )) {
			return false;
		}

		return true;
	}
}
