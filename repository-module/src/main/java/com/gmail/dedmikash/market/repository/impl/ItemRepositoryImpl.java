package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ItemRepository;
import com.gmail.dedmikash.market.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Item> getItems(int page) {
        String selectHqlQuery = "from Item as a ORDER BY a.name ";
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return selectQuery.getResultList();
    }
}
