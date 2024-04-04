package com.blogsystem.annotation.validator;

import com.blogsystem.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{6,}$";

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && !password.isBlank() && Pattern.matches(PASSWORD_REGEX, password);
    }
}
