package com.gmail.dedmikash.market.service.constant;

public class ValidationMessages {
    public static final String USERNAME_EMPTY = "User's email must be not empty";
    public static final String NAME_EMPTY = "User's name must be not empty";
    public static final String SURNAME_EMPTY = "User's surname must be not empty";
    public static final String PATRONYMIC_EMPTY = "User's patronymic must be not empty";
    public static final String COMMENT_EMPTY = "Comment must be not empty";

    public static final String USERNAME_SIZE_NOT_VALID = "User's Email must have from 1 to 50 symbols";
    public static final String NAME_SIZE_NOT_VALID = "User's Name must have from 1 to 20 symbols";
    public static final String SURNAME_SIZE_NOT_VALID = "User's Surname must have from 1 to 40 symbols";
    public static final String PATRONYMIC_SIZE_NOT_VALID = "User's Patronymic must have from 1 to 40 symbols";
    public static final String COMMENT_SIZE_NOT_VALID = "Comment's text must have from 1 to 200 symbols";

    public static final String USERNAME_PATTERN_NOT_VALID = "User's Email must matches pattern:" +
            " ^[a-zA-Z0-9._]+@[a-zA-Z]+.[a-zA-Z]{2,4}$ - simple email pattern.";
    public static final String NAME_PATTERN_NOT_VALID = "User's Name must have only latin letters";
    public static final String SURNAME_PATTERN_NOT_VALID = "User's Surname must have only latin letters";
    public static final String PATRONYMIC_PATTERN_NOT_VALID = "User's Patronymic must have only latin letters";


    public static final String LATIN_REGEX = "[a-zA-z]+";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z]+.[a-zA-Z]{2,4}$";

    private ValidationMessages() {
    }
}