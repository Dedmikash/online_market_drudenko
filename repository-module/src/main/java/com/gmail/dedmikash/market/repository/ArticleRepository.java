package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.Review;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ArticleRepository extends GenericRepository {
    Article add(Connection connection, Article article) throws StatementException;

    Article getArticleById(Connection connection, Long id) throws StatementException;

    List<Article> getAllArticles(Connection connection) throws StatementException;

    void softDeleteById(Connection connection, Long id) throws StatementException;
}
