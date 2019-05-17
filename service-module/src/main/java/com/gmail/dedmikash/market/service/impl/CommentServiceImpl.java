package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.CommentRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.service.CommentService;
import com.gmail.dedmikash.market.service.converter.CommentConverter;
import com.gmail.dedmikash.market.service.exception.DataBaseConnectionException;
import com.gmail.dedmikash.market.service.exception.QueryFailedException;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentConverter commentConverter,
                              CommentRepository commentRepository) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDTO> getCommentsByArticleId(Long articleID) {
        try (Connection connection = commentRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<CommentDTO> comments = getCommentsByArticleId(connection, articleID);
                connection.commit();
                return comments;
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
    public void saveComment(CommentDTO commentDTO) {
        commentDTO.setCreated(new Timestamp(System.currentTimeMillis()));
        commentRepository.create(commentConverter.fromDTO(commentDTO));
    }

    private List<CommentDTO> getCommentsByArticleId(Connection connection, Long articleID) throws StatementException {
        return commentRepository.getCommentsByArticleID(connection, articleID)
                .stream()
                .map(commentConverter::toDTO)
                .collect(Collectors.toList());
    }
}
