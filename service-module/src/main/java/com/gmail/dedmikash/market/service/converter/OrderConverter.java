package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Order;
import com.gmail.dedmikash.market.service.model.OrderDTO;

public interface OrderConverter {
    OrderDTO toDTO(Order order);

    Order fromDTO(OrderDTO orderDTO);
}
