package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;

import java.util.List;

public interface ArticleService {

    void saveArticle(ArticleDTO articleDTO);

    ArticleDTO getArticleById(Long id);

    List<ArticleDTO> getAllArticles();

    PageDTO<ArticleDTO> getArticles(int page, String sort, String order);

    void deleteArticleById(Long id);

    ArticleDTO addCommentToArticle(ArticleDTO articleDTO, CommentDTO commentDTO);

    ArticleDTO changeArticleInfo(Long articleId, ArticleDTO articleDTO);
}
