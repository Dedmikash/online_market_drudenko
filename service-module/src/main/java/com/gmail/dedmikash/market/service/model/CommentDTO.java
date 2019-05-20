package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.COMMENT_EMPTY;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.COMMENT_SIZE_NOT_VALID;

public class CommentDTO {
    private Long id;
    private Long articleID;
    private UserDTO userDTO;
    private Timestamp created;
    @NotNull(message = COMMENT_EMPTY)
    @Size(min = 1, max = 200, message = COMMENT_SIZE_NOT_VALID)
    private String text;
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleID() {
        return articleID;
    }

    public void setArticleID(Long articleID) {
        this.articleID = articleID;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
