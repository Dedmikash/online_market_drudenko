package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Review;
import com.gmail.dedmikash.market.service.converter.ReviewConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {
    private final UserConverter userConverter;

    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public ReviewDTO toDTO(Review review) {
        if (review != null) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setUserDTO(userConverter.toDTO(review.getUser()));
            reviewDTO.setText(review.getText());
            reviewDTO.setCreated(review.getCreated());
            reviewDTO.setVisible(review.isVisible());
            return reviewDTO;
        } else return null;
    }
}
