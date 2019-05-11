package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface UserRepository extends GenericRepository {
    User add(Connection connection, User user) throws StatementException;

    User readByUsername(Connection connection, String username) throws StatementException;

    List<User> getUsers(Connection connection, int page) throws StatementException;

    int getCountOfUsersPages(Connection connection) throws StatementException;

    void softDeleteByIds(Connection connection, Long[] ids) throws StatementException;

    void changeRolesByIds(Connection connection, Map<Long, String> changes) throws StatementException;

    void changePasswordByUsername(Connection connection, String username, String newHashedPassword) throws StatementException;
}
