package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.CommentRepository;
import com.gmail.dedmikash.market.repository.model.Comment;
import com.gmail.dedmikash.market.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        Comment comment = commentRepository.findById(id);
        if (comment != null && !comment.isDeleted()) {
            commentRepository.delete(comment);
        }
    }
}
