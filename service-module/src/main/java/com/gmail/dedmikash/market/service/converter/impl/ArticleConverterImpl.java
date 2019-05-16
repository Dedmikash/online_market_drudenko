package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.service.converter.ArticleConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverterImpl implements ArticleConverter {
    private final UserConverter userConverter;

    public ArticleConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setName(article.getName());
        articleDTO.setUserDTO(userConverter.toDTO(article.getUser()));
        articleDTO.setText(article.getText());
        articleDTO.setCreated(article.getCreated());
        articleDTO.setViews(article.getViews());
        articleDTO.setDeleted(article.isDeleted());
        return articleDTO;
    }

    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setUser(userConverter.fromDTO(articleDTO.getUserDTO()));
        article.setText(articleDTO.getText());
        article.setCreated(articleDTO.getCreated());
        article.setViews(articleDTO.getViews());
        article.setDeleted(articleDTO.isDeleted());
        return article;
    }
}
