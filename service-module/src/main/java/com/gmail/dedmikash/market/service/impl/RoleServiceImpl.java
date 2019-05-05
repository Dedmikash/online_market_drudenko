package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.converter.RoleConverter;
import com.gmail.dedmikash.market.service.exception.DataBaseConnectionException;
import com.gmail.dedmikash.market.service.exception.QueryFailedException;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public List<RoleDTO> getRoles() {
        try (Connection connection = roleRepository.getConnection()) {
            return getRoles(connection);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    private List<RoleDTO> getRoles(Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<RoleDTO> roleDTOList = new ArrayList<>();
            List<Role> roleList = roleRepository.readAll(connection);
            roleList.forEach(role -> roleDTOList.add(roleConverter.toDTO(role)));
            connection.commit();
            return roleDTOList;
        } catch (StatementException e) {
            connection.rollback();
            logger.error(e.getMessage(), e);
            throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
        }
    }
}
