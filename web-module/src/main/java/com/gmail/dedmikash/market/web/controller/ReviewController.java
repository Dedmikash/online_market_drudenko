package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ReviewService;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getReviews(@RequestParam(name = "page", required = false) Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        List<ReviewDTO> reviewDTOList = reviewService.getReviewsBatch(page);
        model.addAttribute("reviews", reviewDTOList);
        model.addAttribute("pages", reviewService.getCountOfReviewsPages());
        logger.info("Getting reviews {}, page {}", reviewDTOList, page);
        return "reviews";
    }

    @PostMapping("/delete")
    public String deleteReviews(@RequestParam(value = "delete", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/reviews";
        }
        logger.info("Deleting reviews with ids: {} ", Arrays.toString(ids));
        reviewService.deleteReviewsByIds(ids);
        return "redirect:/reviews";
    }

    @PostMapping("/change_visibility")
    public String changeVisibility(@RequestParam(value = "old_visibility", required = false) List<String> oldVisibility,
                                   @RequestParam(value = "new_visibility", required = false) List<String> newVisibility) {
        Set ids = reviewService.changeReviewsVisibility(oldVisibility, newVisibility);
        logger.info("Changing visibility of reviews with ids: {} ", ids);
        return "redirect:/reviews";
    }
}
