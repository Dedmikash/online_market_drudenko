package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ReviewRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Review;
import com.gmail.dedmikash.market.service.ReviewService;
import com.gmail.dedmikash.market.service.converter.ReviewConverter;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceUnitTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewConverter reviewConverter;
    @Mock
    private Connection connection;
    private ReviewService reviewService;
    private Review firstReview = new Review(null, "text1", new Timestamp(System.currentTimeMillis()), true, false);
    private Review secondReview = new Review(null, "text2", new Timestamp(System.currentTimeMillis()), false, true);
    private List<Review> reviewList = Arrays.asList(firstReview, secondReview);

    @Before
    public void init() {
        reviewService = new ReviewServiceImpl(reviewConverter, reviewRepository);
    }

    @Test
    public void shouldReturnRightReviewDTOsListWhenGetReviewsBatch() throws StatementException {
        when(reviewRepository.getConnection()).thenReturn(connection);
        when(reviewRepository.readPage(connection, 1)).thenReturn(reviewList);
        ReviewDTO firstReviewDTO = new ReviewDTO(null, "text1", firstReview.getCreated(), true, false);
        ReviewDTO secondReviewDTO = new ReviewDTO(null, "text2", firstReview.getCreated(), false, true);
        when(reviewConverter.toDTO(firstReview)).thenReturn(firstReviewDTO);
        when(reviewConverter.toDTO(secondReview)).thenReturn(secondReviewDTO);
        Assert.assertEquals(firstReviewDTO, reviewService.getReviews(1).getList().get(0));
        Assert.assertEquals(secondReviewDTO, reviewService.getReviews(1).getList().get(1));
    }
}
