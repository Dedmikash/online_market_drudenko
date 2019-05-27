package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {
    @Override
    public User findByUsername(String username) {
        String selectHqlQuery = "from User as u where u.username=:username";
        Query query = entityManager.createQuery(selectHqlQuery);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
