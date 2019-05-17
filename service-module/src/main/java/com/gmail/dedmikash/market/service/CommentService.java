package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentsByArticleId(Long articleID);

    void saveComment(CommentDTO commentDTO);
}
