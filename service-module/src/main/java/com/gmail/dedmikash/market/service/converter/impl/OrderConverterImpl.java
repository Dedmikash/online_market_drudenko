package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.repository.model.Order;
import com.gmail.dedmikash.market.repository.model.Profile;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.converter.ItemConverter;
import com.gmail.dedmikash.market.service.converter.OrderConverter;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.OrderDTO;
import com.gmail.dedmikash.market.service.model.ProfileDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
public class OrderConverterImpl implements OrderConverter {
    private final ItemConverter itemConverter;

    public OrderConverterImpl(ItemConverter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Override
    public OrderDTO toDTO(Order order) {
        if (order != null) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setUniqueNumber(order.getUniqueNumber());
            orderDTO.setStatus(order.getStatus());
            ItemDTO itemDTO = new ItemDTO();
            if (order.getItem() != null) {
                itemDTO = itemConverter.toDTO(order.getItem());
            }
            orderDTO.setItemDTO(itemDTO);
            orderDTO.setQuantity(order.getQuantity());
            orderDTO.setPrice(order.getPrice().toString());
            UserDTO userDTO = new UserDTO();
            if (order.getUser() != null) {
                userDTO.setId(order.getUser().getId());
                userDTO.setName(order.getUser().getName());
                userDTO.setSurname(order.getUser().getSurname());
                ProfileDTO profileDTO = new ProfileDTO();
                if (order.getUser().getProfile() != null) {
                    profileDTO.setTelephone(order.getUser().getProfile().getTelephone());
                    profileDTO.setAddress(order.getUser().getProfile().getAddress());
                }
                userDTO.setProfileDTO(profileDTO);
            }
            orderDTO.setUserDTO(userDTO);
            orderDTO.setCreated(order.getCreated().toString());
            return orderDTO;
        } else return null;
    }

    @Override
    public Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setUniqueNumber(orderDTO.getUniqueNumber());
        order.setStatus(orderDTO.getStatus());
        Item item = new Item();
        if (orderDTO.getItemDTO() != null) {
            item = itemConverter.fromDTO(orderDTO.getItemDTO());
        }
        order.setItem(item);
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(new BigDecimal(orderDTO.getPrice()));
        User user = new User();
        if (orderDTO.getUserDTO() != null) {
            user.setId(orderDTO.getUserDTO().getId());
            user.setName(orderDTO.getUserDTO().getName());
            user.setSurname(orderDTO.getUserDTO().getSurname());
            Profile profile = new Profile();
            if (orderDTO.getUserDTO().getProfileDTO() != null) {
                profile.setTelephone(orderDTO.getUserDTO().getProfileDTO().getTelephone());
                profile.setAddress(orderDTO.getUserDTO().getProfileDTO().getAddress());
            }
            user.setProfile(profile);
        }
        order.setUser(user);
        order.setCreated(Timestamp.valueOf(orderDTO.getCreated()));
        return order;
    }
}
