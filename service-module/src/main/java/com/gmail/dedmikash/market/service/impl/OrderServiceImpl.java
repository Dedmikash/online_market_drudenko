package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ItemRepository;
import com.gmail.dedmikash.market.repository.OrderRepository;
import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.enums.StatusEnum;
import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.repository.model.Order;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.OrderService;
import com.gmail.dedmikash.market.service.converter.OrderConverter;
import com.gmail.dedmikash.market.service.model.OrderDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.util.RandomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final RandomService randomService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderConverter orderConverter,
                            ItemRepository itemRepository,
                            UserRepository userRepository,
                            RandomService randomService) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.randomService = randomService;
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long id) {
        return orderConverter.toDTO(orderRepository.findById(id));
    }

    @Override
    @Transactional
    public OrderDTO getOrderByUniqueNumber(String uniqueNumber) {
        return orderConverter.toDTO(orderRepository.findByUniqueNumber(uniqueNumber));
    }

    @Override
    @Transactional
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getOrders(int page) {
        PageDTO<OrderDTO> orders = new PageDTO<>();
        List<OrderDTO> orderDTOS = getPageOfOrders(page);
        orders.setList(orderDTOS);
        orders.setCountOfPages(orderRepository.getCountOfPages());
        return orders;
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getMyOrders(int page, Long userId) {
        PageDTO<OrderDTO> orders = new PageDTO<>();
        List<OrderDTO> orderDTOS = getPageOfMyOrders(page, userId);
        orders.setList(orderDTOS);
        orders.setCountOfPages(orderRepository.getCountOfMyPages(userId));
        return orders;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(Long userId, Long itemId, int quantity) {
        Order order = new Order();
        order.setStatus(StatusEnum.NEW.name());
        Item item = itemRepository.findById(itemId);
        order.setItem(item);
        order.setQuantity(quantity);
        order.setCreated(new Timestamp(System.currentTimeMillis()));
        order.setPrice(item.getPrice().multiply(new BigDecimal(quantity)));
        User user = userRepository.findById(userId);
        order.setUser(user);
        order.setUniqueNumber(randomService.getUniqueNumber());
        orderRepository.create(order);
        return orderConverter.toDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO changeOrderStatusById(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(newStatus);
        orderRepository.update(order);
        return orderConverter.toDTO(orderRepository.findById(orderId));
    }

    private List<OrderDTO> getPageOfOrders(int page) {
        return orderRepository.getOrders(page)
                .stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    private List<OrderDTO> getPageOfMyOrders(int page, Long userId) {
        return orderRepository.getMyOrders(page, userId)
                .stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }
}
