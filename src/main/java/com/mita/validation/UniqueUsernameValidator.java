package com.mita.validation;

import com.mita.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public boolean isValid(String users, ConstraintValidatorContext context) {
		return !accountService.checkExistingUser(users);
	}

}
