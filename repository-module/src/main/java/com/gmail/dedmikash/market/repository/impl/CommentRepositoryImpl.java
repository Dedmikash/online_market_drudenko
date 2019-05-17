package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.CommentRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.Comment;
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
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    @Override
    public List<Comment> getCommentsByArticleID(Connection connection, Long articleID) throws StatementException {
        String selectQuery = "SELECT * FROM comment c JOIN user u ON c.user_id=u.id" +
                " WHERE c.deleted=0 AND article_id=? ORDER BY created DESC";
        List<Comment> articleList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, articleID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    articleList.add(getComment(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return articleList;
    }

    private Comment getComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("id"));
        Article article = new Article();
        article.setId(resultSet.getLong("article_id"));
        comment.setArticle(article);
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        comment.setUser(user);
        comment.setCreated(resultSet.getTimestamp("created"));
        comment.setText(resultSet.getString("text"));
        comment.setDeleted(resultSet.getBoolean("deleted"));
        return comment;
    }
}
