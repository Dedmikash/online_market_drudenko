package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ReviewService;
import com.gmail.dedmikash.market.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{page}")
    public String getReviews(@PathVariable("page") int page, Model model) {
        List<ReviewDTO> reviewDTOList = reviewService.getReviewsBatch(page);
        model.addAttribute("reviews", reviewDTOList);
        model.addAttribute("pages", reviewService.countPages());
        logger.info("Getting reviews {}, page {}", reviewDTOList, page);
        return "reviews";
    }

    @PostMapping("/delete_reviews")
    public String deleteReviews(@RequestParam(value = "delete", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/reviews/1";
        }
        logger.info("Deleting reviews with ids: {} ", Arrays.toString(ids));
        reviewService.deleteReviewsByIds(ids);
        return "redirect:/reviews/1";
    }

    @PostMapping("/change_visibility")
    public String changeVisibility(@RequestParam(value = "old_visibility", required = false) List<String> oldVisibility,
                                   @RequestParam(value = "new_visibility", required = false) List<String> newVisibility) {
        Map<Long, Boolean> changes = new HashMap<>();
        if (oldVisibility != null) {
            if (newVisibility != null) {
                for (String element : newVisibility) {
                    if (oldVisibility.contains(element)) {
                        oldVisibility.remove(element);
                    } else {
                        logger.info(element);
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
            if (changes.isEmpty()) {
                return "redirect:/reviews/1";
            }
            reviewService.changeReviewsVisibilityById(changes);
            logger.info("Changing visibility of reviews with ids: {} ", changes.keySet());
        }
        return "redirect:/reviews/1";
    }
}
