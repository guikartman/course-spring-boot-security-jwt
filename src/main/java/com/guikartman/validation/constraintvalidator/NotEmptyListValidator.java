package com.guikartman.validation.constraintvalidator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.guikartman.validation.NotEmptyList;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		return list != null && !list.isEmpty();
	}
	
}
