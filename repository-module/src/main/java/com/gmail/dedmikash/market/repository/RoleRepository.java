package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;

import java.sql.Connection;

public interface RoleRepository {
    Role read(Connection connection, Long id) throws StatementException;
}