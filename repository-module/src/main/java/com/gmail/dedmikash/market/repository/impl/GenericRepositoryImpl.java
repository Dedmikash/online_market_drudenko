package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.GenericRepository;
import com.gmail.dedmikash.market.repository.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;

public class GenericRepositoryImpl implements GenericRepository {
    private static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }
}
