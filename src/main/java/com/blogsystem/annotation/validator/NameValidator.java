package com.blogsystem.annotation.validator;

import com.blogsystem.annotation.Name;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name,String> {
    private static final String NAME_REGEX = "^[a-zA-Z]+$";

    @Override
    public void initialize(Name constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null && !name.isBlank() && Pattern.matches(NAME_REGEX, name);
    }
}
