package com.gmail.dedmikash.market.repository;

import com.gmail.dedmikash.market.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    User findNonDeletedByUsername(String username);

    List<User> findNonDeletedPage(int page);

    User findNonDeletedById(Long id);

    int getCountOfNonDeletedPages();
}
