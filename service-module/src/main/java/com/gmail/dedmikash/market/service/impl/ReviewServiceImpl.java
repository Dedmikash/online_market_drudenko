package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ReviewRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.service.ReviewService;
import com.gmail.dedmikash.market.service.converter.ReviewConverter;
import com.gmail.dedmikash.market.service.exception.DataBaseConnectionException;
import com.gmail.dedmikash.market.service.exception.QueryFailedException;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewConverter reviewConverter, ReviewRepository reviewRepository) {
        this.reviewConverter = reviewConverter;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public PageDTO<ReviewDTO> getReviews(int page) {
        try (Connection connection = reviewRepository.getConnection()) {
            try {
                PageDTO<ReviewDTO> reviews = new PageDTO<>();
                connection.setAutoCommit(false);
                reviews.setList(getPageOfReviews(page, connection));
                reviews.setCountOfPages(reviewRepository.getCountOfReviewsPages(connection));
                connection.commit();
                return reviews;
            } catch (StatementException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteReviewsByIds(Long[] ids) {
        for (Long id : ids) {
            reviewRepository.delete(reviewRepository.findById(id));
        }
    }

    @Override
    public Set<Long> changeReviewsVisibility(List<String> oldVisibility, List<String> newVisibility) {
        Map<Long, Boolean> changes = new HashMap<>();
        if (oldVisibility != null) {
            buildChanges(oldVisibility, newVisibility, changes);
            if (!changes.isEmpty()) {
                try (Connection connection = reviewRepository.getConnection()) {
                    try {
                        connection.setAutoCommit(false);
                        reviewRepository.changeVisibilityByIds(connection, changes);
                        connection.commit();
                    } catch (StatementException e) {
                        connection.rollback();
                        logger.error(e.getMessage(), e);
                        throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
                }
            }
        }
        return changes.keySet();
    }

    private void buildChanges(List<String> oldVisibility, List<String> newVisibility, Map<Long, Boolean> changes) {
        if (newVisibility != null) {
            for (String element : newVisibility) {
                if (oldVisibility.contains(element)) {
                    oldVisibility.remove(element);
                } else {
                    changes.put(Long.parseLong(element.split(" ")[0]), true);
                }
            }
        }
        for (String element : oldVisibility) {
            String[] parts = element.split(" ");
            if (parts[1].equals("true")) {
                changes.put(Long.parseLong(parts[0]), false);
            }
        }
    }

    private List<ReviewDTO> getPageOfReviews(int page, Connection connection) throws SQLException, StatementException {
        List<ReviewDTO> reviewDTOList = reviewRepository.readPage(connection, page)
                .stream()
                .map(reviewConverter::toDTO)
                .collect(Collectors.toList());
        connection.commit();
        return reviewDTOList;
    }
}
