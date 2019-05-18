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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final int BATCH_SIZE = 10;

    @Override
    public User readByUsername(Connection connection, String username) throws StatementException {
        String selectQuery = "SELECT *,r.name AS role_name FROM user u JOIN role r ON u.role_id=r.id WHERE username=? AND deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return null;
    }

    @Override
    public List<User> getUsers(Connection connection, int page) throws StatementException {
        String selectQuery = "SELECT *,r.name AS role_name FROM user u JOIN role r ON u.role_id=r.id" +
                " WHERE deleted=0 ORDER BY username LIMIT ?,?";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, getSQLLimit(page));
            preparedStatement.setInt(2, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(getUser(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return userList;
    }

    @Override
    public int getCountOfUsersPages(Connection connection) throws StatementException {
        String countQuery = "SELECT ceil(COUNT(*)/?) AS pages FROM user WHERE deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setInt(1, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("pages");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, countQuery), e);
        }
        return 1;
    }

    @Override
    public void changeRolesByIds(Connection connection, Map<Long, String> changes) throws StatementException {
        for (Map.Entry<Long, String> entry : changes.entrySet()) {
            String updateQuery = "UPDATE user SET role_id=(SELECT id FROM role WHERE name=?) WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, entry.getValue());
                preparedStatement.setLong(2, entry.getKey());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Changing role of user with id: " + entry.getKey() + " - on: "
                            + entry.getValue() + " - failed, no rows affected.");
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, updateQuery), e);
            }
        }
    }

    @Override
    public void changePasswordByUsername(Connection connection, String username, String newHashedPassword) throws StatementException {
        String updateQuery = "UPDATE user SET password=? WHERE username=? AND deleted=0;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, newHashedPassword);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Changing password of user with username: " + username
                        + " - failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, updateQuery), e);
        }
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

    private int getSQLLimit(int page) {
        return -BATCH_SIZE + page * BATCH_SIZE;
    }
}
