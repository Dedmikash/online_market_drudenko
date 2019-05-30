package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    protected static final int BATCH_SIZE = 10;
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings(value = "unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[1];
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<T> findAll() {
        String query = "from " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findPage(int page) {
        String query = "from " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return q.getResultList();
    }

    @Override
    public int getCountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }

    @Override
    public int getCountOfPages() {
        int count = getCountOfEntities();
        return (int) Math.ceil(count / (double) BATCH_SIZE);
    }

    protected int getOffset(int page) {
        return -BATCH_SIZE + page * BATCH_SIZE;
    }
}
