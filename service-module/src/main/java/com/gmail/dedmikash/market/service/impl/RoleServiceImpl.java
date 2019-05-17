package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.converter.RoleConverter;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<RoleDTO> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }
}
