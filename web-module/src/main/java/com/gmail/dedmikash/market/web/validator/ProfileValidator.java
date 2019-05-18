package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.*;

@Component
public class ProfileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", USER_NAME_EMPTY);
        if (user.getName().length() > 20) {
            errors.rejectValue("name", "", USER_NAME_SIZE_NOT_VALID);
        }
        if (!user.getName().matches(LATIN_REGEX)) {
            errors.rejectValue("name", "", NAME_PATTERN_NOT_VALID);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", USER_SURNAME_EMPTY);
        if (user.getSurname().length() > 40) {
            errors.rejectValue("surname", "", USER_SURNAME_SIZE_NOT_VALID);
        }
        if (!user.getSurname().matches(LATIN_REGEX)) {
            errors.rejectValue("surname", "", SURNAME_PATTERN_NOT_VALID);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profileDTO.address", USER_PATRONYMIC_EMPTY);
        if (user.getProfileDTO().getAddress().length() > 100) {
            errors.rejectValue("profileDTO.address", "", USER_ADDRESS_SIZE_NOT_VALID);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profileDTO.telephone", USER_SURNAME_EMPTY);
        if (user.getProfileDTO().getTelephone().length() > 13) {
            errors.rejectValue("profileDTO.telephone", "", USER_TELEPHONE_SIZE_NOT_VALID);
        }
        if (!user.getProfileDTO().getTelephone().matches(PHONE_NUMBER_REGEX)) {
            errors.rejectValue("profileDTO.telephone", "", TELEPHONE_PATTERN_NOT_VALID);
        }
    }
}
