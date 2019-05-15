package com.gmail.dedmikash.market.repository.model;

import java.sql.Timestamp;

public class Review {
    private Long id;
    private User user;
    private String text;
    private Timestamp created;
    private boolean isVisible;
    private boolean isDeleted;

    public Review() {
    }

    public Review(User user, String text, Timestamp created, boolean isVisible, boolean isDeleted) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        isVisible = visible;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
