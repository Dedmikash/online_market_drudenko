package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ReviewRepository;
import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.Review;
import com.gmail.dedmikash.market.service.ReviewService;
import com.gmail.dedmikash.market.service.converter.ReviewConverter;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewConverter reviewConverter,
                             ReviewRepository reviewRepository,
                             UserRepository userRepository) {
        this.reviewConverter = reviewConverter;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ReviewDTO saveReview(ReviewDTO reviewDTO, Long userId) {
        Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setCreated(new Timestamp(System.currentTimeMillis()));
        review.setUser(userRepository.findById(userId));
        review.setVisible(true);
        reviewRepository.create(review);
        return reviewConverter.toDTO(review);
    }

    @Override
    @Transactional
    public PageDTO<ReviewDTO> getReviews(int page) {
        PageDTO<ReviewDTO> reviews = new PageDTO<>();
        List<ReviewDTO> reviewDTOS = getPageOfReviews(page);
        reviews.setList(reviewDTOS);
        reviews.setCountOfPages(reviewRepository.getCountOfPages());
        return reviews;
    }

    @Override
    @Transactional
    public void deleteReviewsByIds(Long[] ids) {
        for (Long id : ids) {
            Review review = reviewRepository.findById(id);
            if (review != null && !review.isDeleted()) {
                reviewRepository.delete(review);
            }
        }
    }

    @Override
    @Transactional
    public Set<Long> changeReviewsVisibility(List<String> oldVisibility, List<String> newVisibility) {
        Map<Long, Boolean> changes = new HashMap<>();
        if (oldVisibility != null) {
            buildChanges(oldVisibility, newVisibility, changes);
            if (!changes.isEmpty()) {
                for (Map.Entry<Long, Boolean> change : changes.entrySet()) {
                    Review review = reviewRepository.findById(change.getKey());
                    review.setVisible(change.getValue());
                    reviewRepository.update(review);
                }
            }
        }
        return changes.keySet();
    }

    private void buildChanges(List<String> oldVisibility, List<String> newVisibility, Map<Long, Boolean> changes) {
        if (newVisibility != null) {
            for (String element : newVisibility) {
                if (oldVisibility.contains(element)) {
                    oldVisibility.remove(element);
                } else {
                    changes.put(Long.parseLong(element.split(" ")[0]), true);
                }
            }
        }
        for (String element : oldVisibility) {
            String[] parts = element.split(" ");
            if (parts[1].equals("true")) {
                changes.put(Long.parseLong(parts[0]), false);
            }
        }
    }

    private List<ReviewDTO> getPageOfReviews(int page) {
        return reviewRepository.findPage(page)
                .stream()
                .map(reviewConverter::toDTO)
                .collect(Collectors.toList());
    }
}
