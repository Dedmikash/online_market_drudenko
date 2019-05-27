package com.gmail.dedmikash.market.repository.impl;

import com.gmail.dedmikash.market.repository.ReviewRepository;
import com.gmail.dedmikash.market.repository.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {
}
