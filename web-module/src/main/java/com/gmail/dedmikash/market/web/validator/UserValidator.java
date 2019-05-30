package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.DUPLICATE_USERNAME;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.EMAIL_REGEX;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.LATIN_REGEX;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_BLOCKED_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_DELETED_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ID_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_NAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_NAME_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_NAME_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_NAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_NAME_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PASSWORD_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PATRONYMIC_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PATRONYMIC_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PATRONYMIC_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PATRONYMIC_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PATRONYMIC_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_PROFILE_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ROLE_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ROLE_ID_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ROLE_NAME_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ROLE_NOT_EXISTS;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_ROLE_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_SURNAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_SURNAME_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_SURNAME_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_SURNAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_SURNAME_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_USERNAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_USERNAME_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_USERNAME_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_USERNAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.USER_USERNAME_SIZE_NOT_VALID;

@Component
public class UserValidator implements Validator {
    private final UserService userService;
    private final RoleService roleService;

    public UserValidator(UserService userService,
                         RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USER_USERNAME_EMPTY);
        if (user.getUsername() == null) {
            errors.rejectValue("username", "", USER_USERNAME_NULL);
        } else {
            if (userService.readByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "", DUPLICATE_USERNAME);
            }
            if (user.getUsername().length() > USER_USERNAME_SIZE) {
                errors.rejectValue("username", "", USER_USERNAME_SIZE_NOT_VALID);
            }
            if (!user.getUsername().matches(EMAIL_REGEX)) {
                errors.rejectValue("username", "", USER_USERNAME_PATTERN_NOT_VALID);
            }
        }

        if (user.getPassword() != null) {
            errors.rejectValue("id", "", USER_PASSWORD_NOT_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", USER_NAME_EMPTY);
        if (user.getName() == null) {
            errors.rejectValue("name", "", USER_NAME_NULL);
        } else {
            if (user.getName().length() > USER_NAME_SIZE) {
                errors.rejectValue("name", "", USER_NAME_SIZE_NOT_VALID);
            }
            if (!user.getName().matches(LATIN_REGEX)) {
                errors.rejectValue("name", "", USER_NAME_PATTERN_NOT_VALID);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", USER_SURNAME_EMPTY);
        if (user.getSurname() == null) {
            errors.rejectValue("surname", "", USER_SURNAME_NULL);
        } else {
            if (user.getSurname().length() > USER_SURNAME_SIZE) {
                errors.rejectValue("surname", "", USER_SURNAME_SIZE_NOT_VALID);
            }
            if (!user.getSurname().matches(LATIN_REGEX)) {
                errors.rejectValue("surname", "", USER_SURNAME_PATTERN_NOT_VALID);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", USER_PATRONYMIC_EMPTY);
        if (user.getPatronymic() == null) {
            errors.rejectValue("patronymic", "", USER_PATRONYMIC_NULL);
        } else {
            if (user.getPatronymic().length() > USER_PATRONYMIC_SIZE) {
                errors.rejectValue("patronymic", "", USER_PATRONYMIC_SIZE_NOT_VALID);
            }
            if (!user.getPatronymic().matches(LATIN_REGEX)) {
                errors.rejectValue("patronymic", "", USER_PATRONYMIC_PATTERN_NOT_VALID);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleDTO", USER_ROLE_EMPTY);
        if (user.getRoleDTO() == null) {
            errors.rejectValue("roleDTO", "", USER_ROLE_NULL);
        } else {
            if (user.getRoleDTO().getId() == null) {
                errors.rejectValue("roleDTO", "", USER_ROLE_ID_NULL);
            } else {
                boolean isRoleExists = false;
                for (RoleDTO role : roleService.getRoles()) {
                    if (role.getId().equals(user.getRoleDTO().getId())) {
                        isRoleExists = true;
                        break;
                    }
                }
                if (!isRoleExists) {
                    errors.rejectValue("roleDTO", "", USER_ROLE_NOT_EXISTS);
                }
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
