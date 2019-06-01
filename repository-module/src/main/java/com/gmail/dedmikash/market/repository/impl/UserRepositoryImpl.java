package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {
    @Override
    public User findNonDeletedByUsername(String username) {
        String selectHqlQuery = "from User as u where u.username=:username and u.isDeleted=0";
        Query query = entityManager.createQuery(selectHqlQuery);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findNonDeletedPage(int page) {
        String query = "from User as u where u.isDeleted=0";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return q.getResultList();
    }

    @Override
    public User findNonDeletedById(Long id) {
        String selectHqlQuery = "from User as u where u.id=:id and u.isDeleted=0";
        Query query = entityManager.createQuery(selectHqlQuery);
        query.setParameter("id", id);
        try {
            return (User) query.getSingleResult();
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
        String query = "SELECT COUNT(*) from User as u where u.isDeleted=0";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
