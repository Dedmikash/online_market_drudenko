package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.REVIEW_TEXT_SIZE;

public class ReviewDTO {
    private Long id;
    private UserDTO userDTO;
    @NotNull
    @Size(min = 1, max = REVIEW_TEXT_SIZE)
    private String text;
    private Timestamp created;
    private boolean isVisible;

    public ReviewDTO() {
    }

    public ReviewDTO(UserDTO userDTO, String text, Timestamp created, boolean isVisible) {
        this.userDTO = userDTO;
        this.text = text;
        this.created = created;
        this.isVisible = isVisible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "userDTO=" + userDTO +
                ", text='" + text + '\'' +
                '}';
    }
}
