package com.gmail.dedmikash.market.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.dedmikash.market.service.constant.ValidationMessages.*;

public class UserDTO {
    private Long id;
    @NotNull(message = USERNAME_EMPTY)
    @Size(min = 1, max = 50, message = USERNAME_SIZE_NOT_VALID)
    @Pattern(regexp = EMAIL_REGEX, message = USERNAME_PATTERN_NOT_VALID)
    private String username;
    private String password;
    @NotNull(message = NAME_EMPTY)
    @Size(min = 1, max = 20, message = NAME_SIZE_NOT_VALID)
    @Pattern(regexp = LATIN_REGEX, message = NAME_PATTERN_NOT_VALID)
    private String name;
    @NotNull(message = SURNAME_EMPTY)
    @Size(min = 1, max = 40, message = SURNAME_SIZE_NOT_VALID)
    @Pattern(regexp = LATIN_REGEX, message = SURNAME_PATTERN_NOT_VALID)
    private String surname;
    @NotNull(message = PATRONYMIC_EMPTY)
    @Size(min = 1, max = 40, message = PATRONYMIC_SIZE_NOT_VALID)
    @Pattern(regexp = LATIN_REGEX, message = PATRONYMIC_PATTERN_NOT_VALID)
    private String patronymic;
    private RoleDTO roleDTO;
    private Boolean blocked;
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return username;
    }
}
