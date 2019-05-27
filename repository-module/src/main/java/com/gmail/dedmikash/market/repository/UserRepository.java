package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.User;

public interface UserRepository extends GenericRepository<Long, User> {
    User findByUsername(String username);
}
