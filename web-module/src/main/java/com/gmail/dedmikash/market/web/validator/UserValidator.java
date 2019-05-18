package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.*;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        if (user.getId() != null) {
            errors.rejectValue("id", "", USER_ID_NOT_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USERNAME_EMPTY);
        if (userService.readByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", DUPLICATE_USERNAME);
        }
        if (user.getUsername().length() > 50) {
            errors.rejectValue("username", "", USERNAME_SIZE_NOT_VALID);
        }
        if (!user.getUsername().matches(EMAIL_REGEX)) {
            errors.rejectValue("username", "", USERNAME_PATTERN_NOT_VALID);
        }

        if (user.getPassword() != null) {
            errors.rejectValue("id", "", USER_PASSWORD_NOT_NULL);
        }

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", USER_PATRONYMIC_EMPTY);
        if (user.getPatronymic().length() > 40) {
            errors.rejectValue("patronymic", "", USER_PATRONYMIC_SIZE_NOT_VALID);
        }
        if (!user.getPatronymic().matches(LATIN_REGEX)) {
            errors.rejectValue("patronymic", "", PATRONYMIC_PATTERN_NOT_VALID);
        }

        if (user.getRoleDTO() == null) {
            errors.rejectValue("roleDTO", "", USER_ROLE_NULL);
        } else {
            if (user.getRoleDTO().getId() == null) {
                errors.rejectValue("roleDTO", "", USER_ROLE_ID_NULL);
            }
            if (user.getRoleDTO().getName() != null) {
                errors.rejectValue("roleDTO", "", USER_ROLE_NAME_NOT_NULL);
            }
        }

        if (user.getProfileDTO() != null) {
            errors.rejectValue("profileDTO", "", USER_PROFILE_NOT_NULL);
        }

        if (user.getProfileDTO() != null) {
            errors.rejectValue("deleted", "", USER_DELETED_NOT_NULL);
        }

        if (user.getProfileDTO() != null) {
            errors.rejectValue("blocked", "", USER_BLOCKED_NOT_NULL);
        }
    }
}
