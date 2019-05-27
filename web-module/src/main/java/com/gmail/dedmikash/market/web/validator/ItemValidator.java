package com.gmail.dedmikash.market.web.validator;

import com.gmail.dedmikash.market.service.model.ItemDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_ID_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_NAME_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_NAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_NAME_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_PRICE_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_PRICE_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_PRICE_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_TEXT_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_TEXT_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_TEXT_SIZE_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ITEM_UNIQUE_NUMBER_NOT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.PRICE_PATTERN_NOT_VALID;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.PRICE_REGEX;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ItemDTO item = (ItemDTO) o;

        if (item.getId() != null) {
            errors.rejectValue("id", ITEM_ID_NOT_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", ITEM_NAME_EMPTY);
        if (item.getName().length() > ITEM_NAME_SIZE) {
            errors.rejectValue("name", "", ITEM_NAME_SIZE_NOT_VALID);
        }

        if (item.getUniqueNumber() != null) {
            errors.rejectValue("uniqueNumber", ITEM_UNIQUE_NUMBER_NOT_NULL);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", ITEM_PRICE_EMPTY);
        if (item.getPrice().length() > ITEM_PRICE_SIZE) {
            errors.rejectValue("price", "", ITEM_PRICE_SIZE_NOT_VALID);
        }
        if (!item.getPrice().matches(PRICE_REGEX)) {
            errors.rejectValue("price", "", PRICE_PATTERN_NOT_VALID);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", ITEM_TEXT_EMPTY);
        if (item.getName().length() > ITEM_TEXT_SIZE) {
            errors.rejectValue("text", "", ITEM_TEXT_SIZE_NOT_VALID);
        }
    }
}
