package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    List<Article> getArticles(int page, String sort, String order);
}
