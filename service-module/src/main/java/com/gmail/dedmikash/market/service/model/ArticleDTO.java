package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ArticleDTO {
    private Long id;
    @Size(min = 1, max = 100)
    private String name;
    private UserDTO userDTO;
    @Size(min = 1, max = 1000)
    private String text;
    private String created;
    private Long views;
    private List<CommentDTO> comments = new ArrayList<>();
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" + name + '}';
    }
}
