package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Review;
import com.gmail.dedmikash.market.service.model.ReviewDTO;

public interface ReviewConverter {
    ReviewDTO toDTO(Review review);
}
