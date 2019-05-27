package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ArticleRepository;
import com.gmail.dedmikash.market.repository.enums.OrderEnum;
import com.gmail.dedmikash.market.repository.enums.SortEnum;
import com.gmail.dedmikash.market.repository.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Article> getArticles(int page, String sort, String order) {
        String selectHqlQuery = "from Article as a ORDER BY ";
        selectHqlQuery = buildSortHqlQuery(
                SortEnum.valueOf(sort.toUpperCase()),
                OrderEnum.valueOf(order.toUpperCase()),
                selectHqlQuery);
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return selectQuery.getResultList();
    }

    private String buildSortHqlQuery(SortEnum sort, OrderEnum order, String selectHqlQuery) {
        switch (sort) {
            case DATE:
                selectHqlQuery = selectHqlQuery.concat("a.created ");
                break;
            case SURNAME:
                selectHqlQuery = selectHqlQuery.concat("a.user.surname ");
                break;
            case VIEWS:
                selectHqlQuery = selectHqlQuery.concat("a.views ");
                break;
        }
        switch (order) {
            case DESC:
                selectHqlQuery = selectHqlQuery.concat("desc");
                break;
            case ASC:
                selectHqlQuery = selectHqlQuery.concat("asc");
                break;
        }
        return selectHqlQuery;
    }
}
