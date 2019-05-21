package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.CommentRepository;
import com.gmail.dedmikash.market.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
}
