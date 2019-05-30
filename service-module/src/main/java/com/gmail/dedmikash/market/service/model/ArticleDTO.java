package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_NAME_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.ARTICLE_TEXT_SIZE;

public class ArticleDTO {
    private Long id;
    @NotNull
    @Size(min = 1, max = ARTICLE_NAME_SIZE)
    private String name;
    private UserDTO userDTO;
    @NotNull
    @Size(min = 1, max = ARTICLE_TEXT_SIZE)
    private String text;
    private String created;
    private Long views;
    private List<CommentDTO> comments = new ArrayList<>();

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

    @Override
    public String toString() {
        return "ArticleDTO{" + name + '}';
    }
}
