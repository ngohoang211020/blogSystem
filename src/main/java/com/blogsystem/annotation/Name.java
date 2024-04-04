package com.blogsystem.annotation;

import com.blogsystem.annotation.validator.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name  {
    String message() default "Invalid name format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
