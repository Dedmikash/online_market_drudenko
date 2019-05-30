package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.COMMENT_TEXT_NULL;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.COMMENT_TEXT_SIZE;
import static com.gmail.dedmikash.market.service.constant.ValidationMessages.COMMENT_TEXT_SIZE_NOT_VALID;

public class CommentDTO {
    private Long id;
    private ArticleDTO articleDTO;
    private UserDTO userDTO;
    private Timestamp created;
    @NotNull(message = COMMENT_TEXT_NULL)
    @Size(min = 1, max = COMMENT_TEXT_SIZE, message = COMMENT_TEXT_SIZE_NOT_VALID)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleDTO getArticleDTO() {
        return articleDTO;
    }

    public void setArticleDTO(ArticleDTO articleDTO) {
        this.articleDTO = articleDTO;
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
}
