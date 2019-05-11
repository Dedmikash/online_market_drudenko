package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl implements RoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Override
    public List<Role> readAll(Connection connection) throws StatementException {
        String selectTableSQL = "SELECT * FROM role";
        List<Role> roles = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    roles.add(getRole(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectTableSQL), e);
        }
        return roles;
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        return role;
    }
}
