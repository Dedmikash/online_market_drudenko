package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.service.converter.ArticleConverter;
import com.gmail.dedmikash.market.service.converter.CommentConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ArticleConverterImpl implements ArticleConverter {
    private final UserConverter userConverter;
    private final CommentConverter commentConverter;

    public ArticleConverterImpl(UserConverter userConverter,
                                CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        if (article != null) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(article.getId());
            articleDTO.setName(article.getName());
            UserDTO userDTO = new UserDTO();
            if (article.getUser() != null) {
                userDTO.setName(article.getUser().getName());
                userDTO.setSurname(article.getUser().getSurname());
            }
            articleDTO.setUserDTO(userDTO);
            articleDTO.setText(article.getText());
            articleDTO.setCreated(article.getCreated().toString());
            articleDTO.setViews(article.getViews());
            articleDTO.setComments(article.getComments().stream()
                    .map(commentConverter::toDTO)
                    .collect(Collectors.toList()));
            return articleDTO;
        } else return null;
    }

    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setName(articleDTO.getName());
        if (articleDTO.getUserDTO() != null) {
            article.setUser(userConverter.fromDTO(articleDTO.getUserDTO()));
        } else {
            article.setUser(null);
        }
        article.setText(articleDTO.getText());
        article.setViews(articleDTO.getViews());
        if (articleDTO.getCreated().matches("^\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d$")) {
            article.setCreated(Timestamp.valueOf(LocalDateTime.parse(articleDTO.getCreated())));
        } else {
            article.setCreated(Timestamp.valueOf(articleDTO.getCreated()));
        }
        article.setComments(articleDTO.getComments().stream()
                .map(commentConverter::fromDTO)
                .collect(Collectors.toList()));
        return article;
    }
}
