package com.gmail.dedmikash.market.service.model.assembly;

import com.gmail.dedmikash.market.service.model.ReviewDTO;

import java.util.List;

public class ReviewsWithPages {
    private List<ReviewDTO> reviewDTOList;
    private int countOfPages;

    public List<ReviewDTO> getReviewDTOList() {
        return reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }

    public int getCountOfPages() {
        return countOfPages;
    }

    public void setCountOfPages(int countOfPages) {
        this.countOfPages = countOfPages;
    }
}
