package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository {
    User add(Connection connection, User user) throws StatementException;

    User readByUsername(Connection connection, String username) throws StatementException;

    List<User> readPage(Connection connection, int page) throws StatementException;

    int countPages(Connection connection) throws StatementException;

    void softDeleteByIds(Connection connection, Long[] ids) throws StatementException;
}
