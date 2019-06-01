package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> getItems(int page);

    Item findByUniqueNumber(String uniqueNumber);
}
