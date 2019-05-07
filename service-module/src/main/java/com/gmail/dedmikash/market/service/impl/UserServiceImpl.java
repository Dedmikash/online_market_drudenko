package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.exception.DataBaseConnectionException;
import com.gmail.dedmikash.market.service.exception.QueryFailedException;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.service.util.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RandomService randomService;


    public UserServiceImpl(UserConverter userConverter,
                           UserRepository userRepository,
                           RandomService randomService) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.randomService = randomService;
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = userConverter.fromDTO(userDTO);
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                String password = randomService.getNewPassword();
                String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
                user.setPassword(hash);
                user.setDeleted(false);
                user.setBlocked(false);
                userRepository.add(connection, user);
                logger.info("User: {} - was created. Password: {} - has been sent on email.",
                        userDTO.getUsername(), password);
                connection.commit();
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public UserDTO readByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userRepository.readByUsername(connection, username);
                if (user == null) {
                    return null;
                }
                UserDTO readedUserDTO = userConverter.toDTO(user);
                connection.commit();
                return readedUserDTO;
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<UserDTO> getUsersBatch(int page) {
        try (Connection connection = userRepository.getConnection()) {
            return getPageOfUsers(page, connection);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public int countPages() {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                int numberOfPages = userRepository.countPages(connection);
                connection.commit();
                return numberOfPages;
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void deleteUsersByIds(Long[] ids) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userRepository.softDeleteByIds(connection, ids);
                connection.commit();
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void changeUsersPasswordsByUsernames(String[] usernames) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Map<String, String> newPasswords = new HashMap<>();
                for (String username : usernames) {
                    newPasswords.put(username, randomService.getNewPassword());
                }
                Map<String, String> newHashes = new HashMap<>();
                for (Map.Entry<String, String> entry : newPasswords.entrySet()) {
                    newHashes.put(entry.getKey(), BCrypt.hashpw(entry.getValue(), BCrypt.gensalt(12)));
                }
                userRepository.changePasswordsByUsernames(connection, newHashes);
                for (Map.Entry<String, String> entry : newPasswords.entrySet()) {
                    logger.info("Password of user with username: {} - was changed to: {}. Email has been sent."
                            , entry.getKey(), entry.getValue());
                }
                connection.commit();
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void changeUsersRolesById(Map<Long, String> changes) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userRepository.changeRolesByIds(connection, changes);
                connection.commit();
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    private List<UserDTO> getPageOfUsers(int page, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<UserDTO> userDTOList = new ArrayList<>();
            List<User> userList = userRepository.readPage(connection, page);
            userList.forEach(user -> userDTOList.add(userConverter.toDTO(user)));
            connection.commit();
            return userDTOList;
        } catch (StatementException e) {
            connection.rollback();
            logger.error(e.getMessage(), e);
            throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
        }
    }
}
