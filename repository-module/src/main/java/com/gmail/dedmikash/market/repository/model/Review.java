package com.gmail.dedmikash.market.repository.model;

import java.sql.Timestamp;

public class Review {
    private Long id;
    private User user;
    private String text;
    private Timestamp created;
    private Boolean visible;
    private Boolean deleted;

    public Review() {
    }

    public Review(User user, String text, Timestamp created, Boolean visible, Boolean deleted) {
        this.user = user;
        this.text = text;
        this.created = created;
        this.visible = visible;
        this.deleted = deleted;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
