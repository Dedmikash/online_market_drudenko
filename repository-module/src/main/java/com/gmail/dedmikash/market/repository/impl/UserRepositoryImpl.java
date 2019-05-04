package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.ADDING_USER_NO_ID_OBTAINED_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.ADDING_USER_NO_ROWS_AFFECTED_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final int BATCH_SIZE = 10;

    @Override
    public User add(Connection connection, User user) throws StatementException {
        String insertTableSQL = "INSERT INTO user (username,password,name,surname,patronymic,role_id,blocked,deleted)" +
                " VALUES (?,?,?,?,?,(SELECT id FROM role WHERE name=?),?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                insertTableSQL,
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPatronymic());
            preparedStatement.setString(6, user.getRole().getName());
            preparedStatement.setBoolean(7, user.getBlocked());
            preparedStatement.setBoolean(8, user.getDeleted());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format(ADDING_USER_NO_ROWS_AFFECTED_ERROR_MESSAGE, user));
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    return user;
                } else {
                    throw new SQLException(String.format(ADDING_USER_NO_ID_OBTAINED_ERROR_MESSAGE, user));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, insertTableSQL), e);
        }
    }

    @Override
    public User readByUsername(Connection connection, String username) throws StatementException {
        String selectTableSQL = "SELECT *,r.name AS role_name FROM user u JOIN role r ON u.role_id=r.id WHERE username=? AND deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectTableSQL), e);
        }
        return null;
    }

    @Override
    public List<User> readPage(Connection connection, int page) throws StatementException {
        String selectTableSQL = "SELECT *,r.name AS role_name FROM user u JOIN role r ON u.role_id=r.id" +
                " WHERE deleted=0 ORDER BY username LIMIT ?,?";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL)) {
            preparedStatement.setInt(1, -BATCH_SIZE + page * BATCH_SIZE);
            preparedStatement.setInt(2, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(getUser(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectTableSQL), e);
        }
        return userList;
    }

    @Override
    public int countPages(Connection connection) throws StatementException {
        String countTableSQL = "SELECT ceil(COUNT(*)/?) AS pages FROM user WHERE deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countTableSQL)) {
            preparedStatement.setInt(1, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("pages");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, countTableSQL), e);
        }
        return 1;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPatronymic(resultSet.getString("patronymic"));
        Role role = new Role();
        role.setId(resultSet.getLong("role_id"));
        role.setName(resultSet.getString("role_name"));
        user.setRole(role);
        user.setBlocked(resultSet.getBoolean("blocked"));
        return user;
    }
}
