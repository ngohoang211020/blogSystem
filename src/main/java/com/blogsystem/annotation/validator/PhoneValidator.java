package com.blogsystem.annotation.validator;

import com.blogsystem.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone,String> {
    private static final String PHONE_REGEX = "^[0-9]{10}$";

    @Override
    public void initialize(Phone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone != null && !phone.isBlank() && Pattern.matches(PHONE_REGEX, phone);
    }
}
