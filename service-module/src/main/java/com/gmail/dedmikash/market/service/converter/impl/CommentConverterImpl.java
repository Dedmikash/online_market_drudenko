package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Comment;
import com.gmail.dedmikash.market.service.converter.ArticleConverter;
import com.gmail.dedmikash.market.service.converter.CommentConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
    private final UserConverter userConverter;
    private final ArticleConverter articleConverter;

    public CommentConverterImpl(UserConverter userConverter, ArticleConverter articleConverter) {
        this.userConverter = userConverter;
        this.articleConverter = articleConverter;
    }

    @Override
    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        if (comment.getArticle() != null) {
            commentDTO.setArticleDTO(articleConverter.toDTO(comment.getArticle()));
        } else {
            commentDTO.setArticleDTO(null);
        }
        if (comment.getUser() != null) {
            commentDTO.setUserDTO(userConverter.toDTO(comment.getUser()));
        } else {
            commentDTO.setUserDTO(null);
        }
        commentDTO.setCreated(comment.getCreated());
        commentDTO.setText(comment.getText());
        commentDTO.setDeleted(comment.isDeleted());
        return commentDTO;
    }

    @Override
    public Comment fromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        if (commentDTO.getArticleDTO() != null) {
            comment.setArticle(articleConverter.fromDTO(commentDTO.getArticleDTO()));
        } else {
            comment.setArticle(null);
        }
        if (commentDTO.getUserDTO() != null) {
            comment.setUser(userConverter.fromDTO(commentDTO.getUserDTO()));
        } else {
            comment.setUser(null);
        }
        comment.setCreated(commentDTO.getCreated());
        comment.setText(commentDTO.getText());
        comment.setDeleted(commentDTO.isDeleted());
        return comment;
    }
}
