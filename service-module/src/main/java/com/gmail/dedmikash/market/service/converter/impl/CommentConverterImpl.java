package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.Comment;
import com.gmail.dedmikash.market.service.converter.CommentConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
    private final UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public CommentDTO toDTO(Comment comment) {
        if (comment != null) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(comment.getArticle().getId());
            commentDTO.setArticleDTO(articleDTO);
            if (comment.getUser() != null) {
                commentDTO.setUserDTO(userConverter.toDTO(comment.getUser()));
            } else {
                commentDTO.setUserDTO(null);
            }
            commentDTO.setCreated(comment.getCreated());
            commentDTO.setText(comment.getText());
            return commentDTO;
        } else return null;
    }

    @Override
    public Comment fromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        Article article = new Article();
        article.setId(commentDTO.getArticleDTO().getId());
        comment.setArticle(article);
        if (commentDTO.getUserDTO() != null) {
            comment.setUser(userConverter.fromDTO(commentDTO.getUserDTO()));
        } else {
            comment.setUser(null);
        }
        comment.setCreated(commentDTO.getCreated());
        comment.setText(commentDTO.getText());
        return comment;
    }
}
