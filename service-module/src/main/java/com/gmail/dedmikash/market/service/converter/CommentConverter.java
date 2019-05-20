package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Comment;
import com.gmail.dedmikash.market.service.model.CommentDTO;

public interface CommentConverter {
    CommentDTO toDTO(Comment comment);

    Comment fromDTO(CommentDTO commentDTO);
}
