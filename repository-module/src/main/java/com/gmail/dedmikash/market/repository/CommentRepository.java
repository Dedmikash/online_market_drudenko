package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Comment;

import java.sql.Connection;
import java.util.List;

public interface CommentRepository extends GenericRepository<Long, Comment> {
    List<Comment> getCommentsByArticleID(Connection connection, Long articleID) throws StatementException;
}
