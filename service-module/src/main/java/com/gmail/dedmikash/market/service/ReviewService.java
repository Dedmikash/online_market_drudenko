package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.ReviewDTO;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    PageDTO<ReviewDTO> getReviews(int page);

    void deleteReviewsByIds(Long[] ids);

    Set<Long> changeReviewsVisibility(List<String> oldVisibility, List<String> newVisibility);
}
