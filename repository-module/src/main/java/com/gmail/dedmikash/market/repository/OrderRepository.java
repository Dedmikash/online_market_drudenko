package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {
    Order findByUniqueNumber(String uniqueNumber);

    List<Order> getOrders(int page);

    List<Order> getMyOrders(int page, Long userId);

    int getCountOfMyPages(Long userId);
}
