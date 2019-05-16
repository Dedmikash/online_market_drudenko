package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends GenericRepository<Long, Role> {
    List<Role> readAll(Connection connection) throws StatementException;
}