package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.ReviewDTO;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    List<ReviewDTO> getReviewsBatch(int page);

    int getCountOfReviewsPages();

    void deleteReviewsByIds(Long[] ids);

    Set<Long> changeReviewsVisibility(List<String> oldVisibility, List<String> newVisibility);
}
