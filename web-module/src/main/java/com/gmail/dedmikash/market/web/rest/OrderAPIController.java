package com.gmail.dedmikash.market.web.rest;

import com.gmail.dedmikash.market.service.OrderService;
import com.gmail.dedmikash.market.service.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderAPIController {
    private static final Logger logger = LoggerFactory.getLogger(OrderAPIController.class);
    private final OrderService orderService;

    public OrderAPIController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity showOrders() {
        List<OrderDTO> orderDTOList = orderService.getAllOrders();
        logger.info("All non deleted orders were shown with REST API");
        return new ResponseEntity(orderDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity showOrderWithId(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        if (orderDTO != null) {
            logger.info("Order with id: {} - was shown with REST API", id);
            return new ResponseEntity(orderDTO, HttpStatus.OK);
        } else {
            logger.info("Order with id: {} - wasn't shown with REST API. No such order or it was soft deleted", id);
            return new ResponseEntity("No order with such id in DB or it was soft deleted", HttpStatus.NOT_FOUND);
        }
    }
}
