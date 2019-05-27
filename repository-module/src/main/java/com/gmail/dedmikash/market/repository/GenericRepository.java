package com.gmail.dedmikash.market.repository;

import java.util.List;

public interface GenericRepository<I, T> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(I id);

    List<T> findAll();

    List<T> findEntitiesWithLimit(int offset, int limit);

    List<T> findPage(int page);

    int getCountOfEntities();

    int getCountOfPages();
}
