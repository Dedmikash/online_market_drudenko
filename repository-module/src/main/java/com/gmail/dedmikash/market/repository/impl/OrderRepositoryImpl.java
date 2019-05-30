package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.OrderRepository;
import com.gmail.dedmikash.market.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {
    @Override
    public Order findByUniqueNumber(String uniqueNumber) {
        String selectHqlQuery = "from Order as a where a.uniqueNumber=:uniqueNumber";
        Query query = entityManager.createQuery(selectHqlQuery);
        query.setParameter("uniqueNumber", uniqueNumber);
        try {
            return (Order) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Order> getOrders(int page) {
        String selectHqlQuery = "from Order as a ORDER BY a.created DESC ";
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return selectQuery.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Order> getMyOrders(int page, Long userId) {
        String selectHqlQuery = "from Order as a WHERE a.user.id=:user_id ORDER BY a.created DESC ";
        Query selectQuery = entityManager.createQuery(selectHqlQuery)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE)
                .setParameter("user_id", userId);
        return selectQuery.getResultList();
    }

    @Override
    public int getCountOfMyPages(Long userId) {
        String selectHqlQuery = "SELECT COUNT(*) from Order as a WHERE a.user.id=:user_id";
        Query q = entityManager.createQuery(selectHqlQuery)
                .setParameter("user_id", userId);
        int count = ((Number) q.getSingleResult()).intValue();
        return (int) Math.ceil(count / (double) BATCH_SIZE);
    }
}
