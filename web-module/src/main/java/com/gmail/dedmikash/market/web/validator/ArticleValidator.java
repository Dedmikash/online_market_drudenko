package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.model.ArticleDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_ID_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_ID_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_USER_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_VIEWS_NOT_NULL;

@Component
public class ArticleValidator implements Validator {
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
        if (article.getName().length() > ARTICLE_NAME_SIZE) {
            errors.rejectValue("name", "", ARTICLE_NAME_SIZE_NOT_VALID);
        }

        if (article.getUserDTO() == null) {
            errors.rejectValue("userDTO", "", ARTICLE_USER_NULL);
        } else if (article.getUserDTO().getId() == null) {
            errors.rejectValue("userDTO", "", ARTICLE_USER_ID_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", ARTICLE_TEXT_EMPTY);
        if (article.getText().length() > ARTICLE_TEXT_SIZE) {
            errors.rejectValue("text", "", ARTICLE_TEXT_SIZE_NOT_VALID);
        }

        if (article.getViews() != null) {
            errors.rejectValue("views", "", ARTICLE_VIEWS_NOT_NULL);
        }
    }
}
