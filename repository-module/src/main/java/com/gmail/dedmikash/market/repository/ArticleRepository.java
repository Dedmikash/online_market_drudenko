package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.Review;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface ArticleRepository extends GenericRepository<Long,Article> {
    List<Article> getArticles(Connection connection, int page, String sort) throws StatementException;

    int getCountOfArticlesPages(Connection connection) throws StatementException;
}
