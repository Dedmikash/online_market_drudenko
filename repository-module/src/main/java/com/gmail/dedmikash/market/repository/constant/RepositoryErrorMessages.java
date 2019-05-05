package com.gmail.dedmikash.market.repository.constant;

public class RepositoryErrorMessages {
    public static final String NO_CONNECTION_ERROR_MESSAGE = "Can't get connection to DataBase.";
    public static final String QUERY_FAILED_ERROR_MESSAGE = "Query: {} - failed and rollbacked.";
    public static final String ADDING_USER_NO_ROWS_AFFECTED_ERROR_MESSAGE = "Adding user failed, no rows affected. User: {}. ";
    public static final String ADDING_USER_NO_ID_OBTAINED_ERROR_MESSAGE = "Adding user: {} - failed, no ID obtained.";

    private RepositoryErrorMessages() {
    }
}
