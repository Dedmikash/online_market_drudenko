package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.OrderDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrderById(Long id);

    OrderDTO getOrderByUniqueNumber(String uniqueNumber);

    List<OrderDTO> getAllOrders();

    PageDTO<OrderDTO> getOrders(int page);

    PageDTO<OrderDTO> getMyOrders(int page, Long userId);

    OrderDTO createOrder(Long userId, Long itemId, int quantity);

    OrderDTO changeOrderStatusById(Long orderId, String newStatus);
}
