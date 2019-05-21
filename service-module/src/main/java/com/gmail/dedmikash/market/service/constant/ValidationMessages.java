package com.gmail.dedmikash.market.service.constant;

import org.springframework.stereotype.Component;

@Component
public class ValidationMessages {
    public static final String DUPLICATE_USERNAME = "User with such username already exists in DB";

    public static final String USER_ROLE_NULL = "User's role must be not null";
    public static final String USER_ROLE_ID_NULL = "User's role id must be not null";
    public static final String ARTICLE_USER_NULL = "Article's user must be not null";
    public static final String ARTICLE_USER_ID_NULL = "Article's user id must be not null";

    public static final String USER_ID_NOT_NULL = "User's id must be null";
    public static final String USER_PASSWORD_NOT_NULL = "User's password must be null";
    public static final String USER_ROLE_NAME_NOT_NULL = "User's role name must be null";
    public static final String USER_PROFILE_NOT_NULL = "User's profile must be null";
    public static final String USER_DELETED_NOT_NULL = "User's deleted must be null";
    public static final String USER_BLOCKED_NOT_NULL = "User's blocked must be null";
    public static final String ARTICLE_ID_NOT_NULL = "Article's id must be null";
    public static final String ARTICLE_VIEWS_NOT_NULL = "Article's views must be null";
    public static final String ITEM_ID_NOT_NULL = "Item's id must be null";
    public static final String ITEM_UNIQUE_NUMBER_NOT_NULL = "Item's unique number must be null";

    public static final String USERNAME_EMPTY = "User's email must be not empty";
    public static final String USER_NAME_EMPTY = "User's name must be not empty";
    public static final String USER_SURNAME_EMPTY = "User's surname must be not empty";
    public static final String USER_PATRONYMIC_EMPTY = "User's patronymic must be not empty";
    public static final String COMMENT_EMPTY = "Comment must be not empty";
    public static final String ARTICLE_NAME_EMPTY = "Article's name must be not empty";
    public static final String ARTICLE_TEXT_EMPTY = "Article's text must be not empty";
    public static final String ARTICLE_DATE_EMPTY = "Article's date must be not empty";
    public static final String ITEM_NAME_EMPTY = "Item's name must be not empty";
    public static final String ITEM_PRICE_EMPTY = "Item's price must be not empty";

    public static final String USERNAME_SIZE_NOT_VALID = "User's Email must have from 1 to 50 symbols";
    public static final String USER_NAME_SIZE_NOT_VALID = "User's Name must have from 1 to 20 symbols";
    public static final String USER_SURNAME_SIZE_NOT_VALID = "User's Surname must have from 1 to 40 symbols";
    public static final String USER_PATRONYMIC_SIZE_NOT_VALID = "User's Patronymic must have from 1 to 40 symbols";
    public static final String COMMENT_SIZE_NOT_VALID = "Comment's text must have from 1 to 200 symbols";
    public static final String ARTICLE_NAME_SIZE_NOT_VALID = "Article's name must have from 1 to 100 symbols";
    public static final String ARTICLE_TEXT_SIZE_NOT_VALID = "Article's text must have from 1 to 1000 symbols";
    public static final String USER_ADDRESS_SIZE_NOT_VALID = "User's address must have from 1 to 100 symbols";
    public static final String USER_TELEPHONE_SIZE_NOT_VALID = "User's telephone number  must have from 1 to 13 symbols";
    public static final String ITEM_NAME_SIZE_NOT_VALID = "Item's name must have from 1 to 50 symbols";
    public static final String ITEM_PRICE_SIZE_NOT_VALID = "Item's price must have from 1 to 15 symbols";

    public static final String USERNAME_PATTERN_NOT_VALID = "User's Email must matches pattern:" +
            " ^[a-zA-Z0-9._]+@[a-zA-Z]+.[a-zA-Z]{2,4}$ - simple email pattern.";
    public static final String NAME_PATTERN_NOT_VALID = "User's Name must have only latin letters";
    public static final String SURNAME_PATTERN_NOT_VALID = "User's Surname must have only latin letters";
    public static final String PATRONYMIC_PATTERN_NOT_VALID = "User's Patronymic must have only latin letters";
    public static final String TELEPHONE_PATTERN_NOT_VALID = "User's telephone number must matches pattern: ^\\+375[0-9]{9}$";
    public static final String ARTICLE_DATE_PATTERN_NOT_VALID = "Article's date must matches pattern: ^yyyy-MM-ddTHH:mm$";
    public static final String PRICE_PATTERN_NOT_VALID = "Item's price must matches pattern: ^\\d{1,10}(\\.\\d{1,5})?$";

    public static final String LATIN_REGEX = "[a-zA-z]+";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z]+.[a-zA-Z]{2,4}$";
    public static final String PHONE_NUMBER_REGEX = "^\\+375[0-9]{9}$";
    public static final String PRICE_REGEX = "^\\d{1,10}(\\.\\d{1,5})?$";

    public static final int ARTICLE_NAME_SIZE = 100;
    public static final int ARTICLE_TEXT_SIZE = 1000;
    public static final int USER_NAME_SIZE = 20;
    public static final int USER_SURNAME_SIZE = 40;
    public static final int USER_ADDRESS_SIZE = 100;
    public static final int USER_TELEPHONE_SIZE = 13;
    public static final int USERNAME_SIZE = 50;
    public static final int USER_PATRONYMIC_SIZE = 40;
    public static final int ITEM_NAME_SIZE = 50;
    public static final int ITEM_PRICE_SIZE = 15;

    private ValidationMessages() {
    }
}