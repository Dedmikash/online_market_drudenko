package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.assembly.ReviewsWithPages;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    ReviewsWithPages getReviews(int page);

    void deleteReviewsByIds(Long[] ids);

    Set<Long> changeReviewsVisibility(List<String> oldVisibility, List<String> newVisibility);
}
