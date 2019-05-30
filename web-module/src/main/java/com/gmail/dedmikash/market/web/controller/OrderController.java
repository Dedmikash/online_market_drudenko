package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.OrderService;
import com.gmail.dedmikash.market.service.model.AppUserPrincipal;
import com.gmail.dedmikash.market.service.model.OrderDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dedmikash.market.web.constant.RolesConstants.SALE;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                            Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = ((AppUserPrincipal) userDetails).getAuthorities();
        List<String> stringAuthorities = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        PageDTO<OrderDTO> orders;
        if (stringAuthorities.contains(SALE)) {
            orders = orderService.getOrders(page);
        } else {
            Long id = ((AppUserPrincipal) userDetails).getId();
            orders = orderService.getMyOrders(page, id);
        }
        model.addAttribute("orders", orders.getList());
        model.addAttribute("pages", orders.getCountOfPages());
        logger.info("Getting orders {}, page {}", orders.getList(), page);
        return "orders";
    }

    @GetMapping("/order")
    public String getOrderById(@RequestParam(name = "unique_number") String uniqueNumber,
                               Model model) {
        OrderDTO order = orderService.getOrderByUniqueNumber(uniqueNumber);
        model.addAttribute("order", order);
        logger.info("Getting order with unique number {}", order);
        return "order";
    }

    @PostMapping("/order/change")
    public String changeOrderStatusById(@RequestParam(name = "order_id") Long orderId,
                                        @RequestParam(name = "new_status") String newStatus,
                                        Model model) {
        model.addAttribute("order", orderService.changeOrderStatusById(orderId, newStatus));
        logger.info("Changing order status with id {} on {}", orderId, newStatus);
        return "order";
    }
}
