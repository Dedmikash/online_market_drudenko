package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.ReviewDTO;

import java.util.List;
import java.util.Set;

public interface ArticleService {

    void add(ArticleDTO articleDTO);

    ArticleDTO getArticleById(Long id);

    List<ArticleDTO> getAllArticles();

    void deleteArticleById(Long id);
}
