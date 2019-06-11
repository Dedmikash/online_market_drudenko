package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> getNonDeletedItems(int page);

    Item findNonDeletedById(Long id);

    Item findNonDeletedByUniqueNumber(String uniqueNumber);

    int getCountOfNonDeletedPages();

    List<Item> findAllNonDeleted();
}
