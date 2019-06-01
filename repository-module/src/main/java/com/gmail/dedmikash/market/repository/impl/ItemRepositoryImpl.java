package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ItemRepository;
import com.gmail.dedmikash.market.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Item> getNonDeletedItems(int page) {
        String selectHqlQuery = "from Item as a where a.isDeleted=0 ORDER BY a.name ";
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return selectQuery.getResultList();
    }

    @Override
    public Item findNonDeletedById(Long id) {
        String selectHqlQuery = "from Item as u where u.id=:id and u.isDeleted=0";
        Query query = entityManager.createQuery(selectHqlQuery);
        query.setParameter("id", id);
        try {
            return (Item) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Item findNonDeletedByUniqueNumber(String uniqueNumber) {
        String selectHqlQuery = "from Item as a where a.uniqueNumber=:uniqueNumber and a.isDeleted=0";
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setParameter("uniqueNumber", uniqueNumber);
        try {
            return (Item) selectQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public int getCountOfNonDeletedPages() {
        int count = getCountOfNonDeletedEntities();
        return (int) Math.ceil(count / (double) BATCH_SIZE);
    }

    private int getCountOfNonDeletedEntities() {
        String query = "SELECT COUNT(*) from Item as u where u.isDeleted=0";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
