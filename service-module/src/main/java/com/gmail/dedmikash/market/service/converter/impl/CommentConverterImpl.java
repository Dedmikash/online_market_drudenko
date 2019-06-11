package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.repository.model.Comment;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.converter.CommentConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
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
            if (comment.getArticle() != null) {
                articleDTO.setId(comment.getArticle().getId());
            }
            commentDTO.setArticleDTO(articleDTO);
            UserDTO userDTO = new UserDTO();
            if (comment.getUser() != null) {
                userDTO.setName(comment.getUser().getName());
                userDTO.setSurname(comment.getUser().getSurname());
            }
            commentDTO.setUserDTO(userDTO);
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
        if (commentDTO.getArticleDTO() != null) {
            article.setId(commentDTO.getArticleDTO().getId());
        }
        comment.setArticle(article);
        if (commentDTO.getUserDTO() != null) {
            comment.setUser(userConverter.fromDTO(commentDTO.getUserDTO()));
        } else {
            comment.setUser(new User());
        }
        comment.setCreated(commentDTO.getCreated());
        comment.setText(commentDTO.getText());
        return comment;
    }
}
