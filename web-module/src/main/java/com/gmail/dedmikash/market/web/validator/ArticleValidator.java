package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_COMMENTS_NOT_NULL_OR_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_DATE_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_DATE_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_DATE_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_ID_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_ID_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_NAME_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_PASSWORD_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_PATRONYMIC_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_PROFILE_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_ROLE_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_SURNAME_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_USERNAME_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_VIEWS_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.NO_USER_WITH_ID_IN_DB;

@Component
public class ArticleValidator implements Validator {
    private final UserService userService;

    public ArticleValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ArticleDTO article = (ArticleDTO) o;

        if (article.getId() != null) {
            errors.rejectValue("id", ARTICLE_ID_NOT_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", ARTICLE_NAME_EMPTY);
        if (article.getName() == null) {
            errors.rejectValue("name", "", ARTICLE_NAME_NULL);
        } else {
            if (article.getName().length() > ARTICLE_NAME_SIZE) {
                errors.rejectValue("name", "", ARTICLE_NAME_SIZE_NOT_VALID);
            }
        }

        if (article.getUserDTO() == null) {
            errors.rejectValue("userDTO", "", ARTICLE_USER_NULL);
        } else {
            if (article.getUserDTO().getId() == null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_ID_NULL);
            } else {
                if (userService.getUserById(article.getUserDTO().getId()) == null) {
                    errors.rejectValue("userDTO", "", NO_USER_WITH_ID_IN_DB);
                }
            }
            if (article.getUserDTO().getUsername() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_USERNAME_NOT_NULL);
            }
            if (article.getUserDTO().getPassword() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_PASSWORD_NOT_NULL);
            }
            if (article.getUserDTO().getName() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_NAME_NOT_NULL);
            }
            if (article.getUserDTO().getSurname() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_SURNAME_NOT_NULL);
            }
            if (article.getUserDTO().getPatronymic() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_PATRONYMIC_NOT_NULL);
            }
            if (article.getUserDTO().getRoleDTO() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_ROLE_NOT_NULL);
            }
            if (article.getUserDTO().getProfileDTO() != null) {
                errors.rejectValue("userDTO", "", ARTICLE_USER_PROFILE_NOT_NULL);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", ARTICLE_TEXT_EMPTY);
        if (article.getText() == null) {
            errors.rejectValue("text", "", ARTICLE_TEXT_NULL);
        } else {
            if (article.getText().length() > ARTICLE_TEXT_SIZE) {
                errors.rejectValue("text", "", ARTICLE_TEXT_SIZE_NOT_VALID);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "created", ARTICLE_DATE_EMPTY);
        if (article.getCreated() == null) {
            errors.rejectValue("created", "", ARTICLE_DATE_NULL);
        } else {
            try {
                LocalDateTime.parse(article.getCreated());
            } catch (DateTimeParseException e) {
                errors.rejectValue("created", "", ARTICLE_DATE_PATTERN_NOT_VALID);
            }
        }

        if (article.getViews() != null) {
            errors.rejectValue("views", "", ARTICLE_VIEWS_NOT_NULL);
        }

        if (article.getComments() != null) if (!article.getComments().isEmpty()) {
            errors.rejectValue("views", "", ARTICLE_COMMENTS_NOT_NULL_OR_EMPTY);
        }
    }
}
