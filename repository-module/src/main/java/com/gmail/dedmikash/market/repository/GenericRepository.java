package com.gmail.dedmikash.market.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<I, T> {
    Connection getConnection();

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(I id);

    List<T> findAll();
}
