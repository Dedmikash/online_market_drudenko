package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
