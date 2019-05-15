package com.gmail.dedmikash.market.repository.constant;

public class RepositoryErrorMessages {
    public static final String NO_CONNECTION_ERROR_MESSAGE = "Can't get connection to DataBase.";
    public static final String QUERY_FAILED_ERROR_MESSAGE = "Query: %s - failed and rollbacked.";
    public static final String ADDING_USER_NO_ROWS_AFFECTED_ERROR_MESSAGE = "Adding user failed, no rows affected. User: %s. ";
    public static final String ADDING_USER_NO_ID_OBTAINED_ERROR_MESSAGE = "Adding user: %s - failed, no ID obtained.";
    public static final String ADDING_ARTICLE_NO_ROWS_AFFECTED_ERROR_MESSAGE = "Adding article failed, no rows affected. Article: %s. ";
    public static final String ADDING_ARTICLE_NO_ID_OBTAINED_ERROR_MESSAGE = "Adding article: %s - failed, no ID obtained.";

    private RepositoryErrorMessages() {
    }
}
