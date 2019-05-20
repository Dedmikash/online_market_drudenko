package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.GenericRepository;
import com.gmail.dedmikash.market.repository.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.gmail.dedmikash.market.repository.constant.RepositoryErrorMessages.NO_CONNECTION_ERROR_MESSAGE;

public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    private static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
    @Autowired
    private DataSource dataSource;
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(NO_CONNECTION_ERROR_MESSAGE, e);
        }
    }

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
}
