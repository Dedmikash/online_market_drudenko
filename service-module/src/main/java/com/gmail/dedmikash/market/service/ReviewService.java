package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.ReviewDTO;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    List<ReviewDTO> getReviewsBatch(int page);

    int countPages();

    void deleteReviewsByIds(Long[] ids);

    void changeReviewsVisibilityById(Map<Long, Boolean> changes);
}
