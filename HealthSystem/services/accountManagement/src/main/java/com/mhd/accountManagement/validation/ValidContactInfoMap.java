package com.mhd.accountManagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContactInfoMapValidator.class)

//It means the custom annotation you're creating can be applied to variables declared inside a class or a record (i.e., fields or attributes of an object).
@Target({ElementType.FIELD})

//This means the annotation is retained at runtime, which is required for validation to work, since Jakarta Validator runs during runtime.
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContactInfoMap {
    String message() default "Invalid contact info";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
