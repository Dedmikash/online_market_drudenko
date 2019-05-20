package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.service.model.ArticleDTO;

public interface ArticleConverter {
    ArticleDTO toDTO(Article article);

    Article fromDTO(ArticleDTO articleDTO);
}
