package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Review;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ReviewRepository extends GenericRepository {
    List<Review> readPage(Connection connection, int page) throws StatementException;

    int countPages(Connection connection) throws StatementException;

    void softDeleteByIds(Connection connection, Long[] ids) throws StatementException;

    void changeVisibilityByIds(Connection connection, Map<Long, Boolean> changes) throws StatementException;
}
