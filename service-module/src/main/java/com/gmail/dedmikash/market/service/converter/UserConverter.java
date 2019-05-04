package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.model.UserDTO;

public interface UserConverter {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
