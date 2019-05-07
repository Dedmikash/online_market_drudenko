package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ReviewRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Review;
import com.gmail.dedmikash.market.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {
    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
    private static final int BATCH_SIZE = 10;

    @Override
    public List<Review> readPage(Connection connection, int page) throws StatementException {
        String selectQuery = "SELECT *,r.id AS review_id,r.deleted AS review_deleted FROM review r" +
                " JOIN user u ON r.user_id=u.id WHERE r.deleted=0 ORDER BY created LIMIT ?,?";
        List<Review> reviewList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, -BATCH_SIZE + page * BATCH_SIZE);
            preparedStatement.setInt(2, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(getReview(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, selectQuery), e);
        }
        return reviewList;
    }

    @Override
    public int countPages(Connection connection) throws StatementException {
        String countQuery = "SELECT ceil(COUNT(*)/?) AS pages FROM review WHERE deleted=0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setInt(1, BATCH_SIZE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("pages");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, countQuery), e);
        }
        return 1;
    }

    @Override
    public void softDeleteByIds(Connection connection, Long[] ids) throws StatementException {
        StringBuilder updateQueryBuilder = new StringBuilder("UPDATE review SET deleted=1 WHERE ");
        for (int i = 0; i < ids.length; i++) {
            updateQueryBuilder.append("id=? OR ");
        }
        String updateQuery = updateQueryBuilder.substring(0, updateQueryBuilder.length() - 4);
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            for (int i = 1; i < ids.length + 1; i++) {
                preparedStatement.setLong(i, ids[i - 1]);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Soft deleting reviews with ids: " + Arrays.toString(ids) + " - failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, updateQuery), e);
        }
    }

    @Override
    public void changeVisibilityByIds(Connection connection, Map<Long, Boolean> changes) throws StatementException {
        for (Map.Entry<Long, Boolean> entry : changes.entrySet()) {
            String updateQuery = "UPDATE review SET visible=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setBoolean(1, entry.getValue());
                preparedStatement.setLong(2, entry.getKey());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Changing visibility of review with id: " + entry.getKey() + " - on: "
                            + entry.getValue() + " - failed, no rows affected.");
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new StatementException(String.format(QUERY_FAILED_ERROR_MESSAGE, updateQuery), e);
            }
        }
    }

    private Review getReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("review_id"));
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPatronymic(resultSet.getString("patronymic"));
        review.setUser(user);
        review.setText(resultSet.getString("text"));
        review.setCreated(resultSet.getTimestamp("created"));
        review.setVisible(resultSet.getBoolean("visible"));
        review.setDeleted(resultSet.getBoolean("deleted"));
        return review;
    }
}
