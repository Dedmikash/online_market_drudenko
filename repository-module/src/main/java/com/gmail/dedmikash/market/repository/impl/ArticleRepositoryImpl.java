package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ArticleRepository;
import com.gmail.dedmikash.market.repository.enums.OrderEnum;
import com.gmail.dedmikash.market.repository.enums.SortEnum;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.User;
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
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);
    private static final int BATCH_SIZE = 10;

    @Override
    public List<Article> getArticles(Connection connection, int page, String sort, String order) throws StatementException {
        String selectQuery = "SELECT *,a.id AS article_id,a.deleted AS article_deleted,a.name AS article_name,u.name" +
                " AS user_name FROM article a JOIN user u ON a.user_id=u.id WHERE a.deleted=0 ORDER BY ";
        List<Article> articleList = new ArrayList<>();
        selectQuery = buildSortQuery(
                SortEnum.valueOf(sort.toUpperCase()),
                OrderEnum.valueOf(order.toUpperCase()),
                selectQuery);
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, getSQLLimit(page));
            preparedStatement.setInt(2, BATCH_SIZE);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    articleList.add(getArticle(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return articleList;
    }

    private String buildSortQuery(SortEnum sort, OrderEnum order, String selectQuery) {
        switch (sort) {
            case DATE:
                selectQuery = selectQuery.concat("created ");
                break;
            case SURNAME:
                selectQuery = selectQuery.concat("surname ");
                break;
            case VIEWS:
                selectQuery = selectQuery.concat("views ");
                break;
        }
        switch (order) {
            case DESC:
                selectQuery = selectQuery.concat("DESC ");
                break;
            case ASC:
                selectQuery = selectQuery.concat("ASC ");
                break;
        }
        selectQuery = selectQuery.concat("LIMIT ?,?");
        return selectQuery;
    }

    @Override
    public int getCountOfArticlesPages(Connection connection) throws StatementException {
        String countQuery = "SELECT COUNT(*) AS row_count FROM article WHERE deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return (int) Math.ceil(resultSet.getInt("row_count") / (double) BATCH_SIZE);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, countQuery), e);
        }
        return 1;
    }

    private Article getArticle(ResultSet resultSet) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getLong("article_id"));
        article.setName(resultSet.getString("article_name"));
        User user = new User();
        user.setName(resultSet.getString("user_name"));
        user.setSurname(resultSet.getString("surname"));
        article.setUser(user);
        article.setText(resultSet.getString("text"));
        article.setCreated(resultSet.getTimestamp("created"));
        article.setViews(resultSet.getLong("views"));
        article.setDeleted(resultSet.getBoolean("article_deleted"));
        return article;
    }

    private int getSQLLimit(int page) {
        return -BATCH_SIZE + page * BATCH_SIZE;
    }
}
