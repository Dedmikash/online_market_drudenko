package com.gmail.dedmikash.market.service.model;

import java.sql.Timestamp;

public class ReviewDTO {
    private Long id;
    private UserDTO userDTO;
    private String text;
    private Timestamp created;
    private boolean isVisible;
    private boolean isDeleted;

    public ReviewDTO() {
    }

    public ReviewDTO(UserDTO userDTO, String text, Timestamp created, boolean isVisible, boolean isDeleted) {
        this.userDTO = userDTO;
        this.text = text;
        this.created = created;
        this.isVisible = isVisible;
        this.isDeleted = isDeleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "userDTO=" + userDTO +
                ", text='" + text + '\'' +
                '}';
    }
}
