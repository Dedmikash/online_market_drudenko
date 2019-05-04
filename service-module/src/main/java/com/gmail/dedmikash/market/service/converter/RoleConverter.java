package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.service.model.RoleDTO;

public interface RoleConverter {
    RoleDTO toDTO(Role role);
}
