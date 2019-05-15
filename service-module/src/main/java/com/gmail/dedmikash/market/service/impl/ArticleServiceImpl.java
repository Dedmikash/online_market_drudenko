package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ArticleRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.service.ArticleService;
import com.gmail.dedmikash.market.service.converter.ArticleConverter;
import com.gmail.dedmikash.market.service.exception.DataBaseConnectionException;
import com.gmail.dedmikash.market.service.exception.QueryFailedException;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;
import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.QUERY_FAILED_ERROR_MESSAGE;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private final ArticleConverter articleConverter;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleConverter articleConverter, ArticleRepository articleRepository) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
    }

    @Override
    public void add(ArticleDTO articleDTO) {
        Article article = articleConverter.fromDTO(articleDTO);
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                articleRepository.add(connection, article);
                logger.info("Article: {} - was created.", articleDTO.getName());
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

    @Override
    public ArticleDTO getArticleById(Long id) {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Article article = articleRepository.getArticleById(connection, id);
                if (article == null) {
                    return null;
                }
                ArticleDTO readedArticleDTO = articleConverter.toDTO(article);
                connection.commit();
                return readedArticleDTO;
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
    public List<ArticleDTO> getAllArticles() {
        try (Connection connection = articleRepository.getConnection()) {
            return getAllArticles(connection);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void deleteArticleById(Long id) {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                articleRepository.softDeleteById(connection, id);
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

    private List<ArticleDTO> getAllArticles(Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<ArticleDTO> articleDTOList = articleRepository.getAllArticles(connection)
                    .stream()
                    .map(articleConverter::toDTO)
                    .collect(Collectors.toList());
            connection.commit();
            return articleDTOList;
        } catch (StatementException e) {
            connection.rollback();
            logger.error(e.getMessage(), e);
            throw new QueryFailedException(QUERY_FAILED_ERROR_MESSAGE, e);
        }
    }
}
