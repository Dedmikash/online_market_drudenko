package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ArticleRepository;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.ADDING_ARTICLE_NO_ID_OBTAINED_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.ADDING_ARTICLE_NO_ROWS_AFFECTED_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    @Override
    public Article add(Connection connection, Article article) throws StatementException {
        String insertQuery = "INSERT INTO article (name,user_id,text,created) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                insertQuery,
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, article.getName());
            preparedStatement.setLong(2, article.getUser().getId());
            preparedStatement.setString(3, article.getText());
            preparedStatement.setTimestamp(4, article.getCreated());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format(ADDING_ARTICLE_NO_ROWS_AFFECTED_ERROR_MESSAGE, article));
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    article.setId(generatedKeys.getLong(1));
                    return article;
                } else {
                    throw new SQLException(String.format(ADDING_ARTICLE_NO_ID_OBTAINED_ERROR_MESSAGE, article));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, insertQuery), e);
        }
    }

    @Override
    public Article getArticleById(Connection connection, Long id) throws StatementException {
        String selectQuery = "SELECT *,a.id AS article_id,a.deleted AS article_deleted,a.name AS article_name" +
                ",u.name AS user_name FROM article a JOIN user u ON a.user_id=u.id WHERE a.deleted=0 AND id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getArticle(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return null;
    }

    @Override
    public List<Article> getAllArticles(Connection connection) throws StatementException {
        String selectQuery = "SELECT *,a.id AS article_id,a.deleted AS article_deleted,a.name AS article_name" +
                ",u.name AS user_name FROM article a JOIN user u ON a.user_id=u.id WHERE a.deleted=0";
        List<Article> articleList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
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

    @Override
    public void softDeleteById(Connection connection, Long id) throws StatementException {
        String updateQuery = "UPDATE article SET deleted=1 WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Soft deleting article with id: " + id + " - failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, updateQuery), e);
        }
    }

    private Article getArticle(ResultSet resultSet) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getLong("article_id"));
        article.setName(resultSet.getString("article_name"));
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        article.setUser(user);
        article.setText(resultSet.getString("text"));
        article.setCreated(resultSet.getTimestamp("created"));
        article.setDeleted(resultSet.getBoolean("article_deleted"));
        return article;
    }
}
