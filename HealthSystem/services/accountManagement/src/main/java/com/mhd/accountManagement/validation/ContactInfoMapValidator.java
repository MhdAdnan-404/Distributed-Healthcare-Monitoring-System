package com.mhd.accountManagement.validation;

import com.mhd.accountManagement.model.Enums.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class ContactInfoMapValidator implements ConstraintValidator<ValidContactInfoMap, Map<ContactType, String>> {

    @Override
    public boolean isValid(Map<ContactType, String> contactTypeStringMap, ConstraintValidatorContext constraintValidatorContext) {
        if (contactTypeStringMap == null || contactTypeStringMap.isEmpty()) return false;

        String phoneRegex = "^\\+\\d{10,15}$";
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.\\w+$";

        for (Map.Entry<ContactType, String> entry : contactTypeStringMap.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.isBlank()) return false;

            switch (entry.getKey()) {
                case PHONE:
                    if (!value.matches(phoneRegex)) return false;
                    break;
                case EMAIL:
                    if (!value.matches(emailRegex)) return false;
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
