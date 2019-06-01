package com.gmail.dedmikash.market.repository.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
@SQLDelete(sql = "UPDATE profile SET deleted = 1 WHERE user_id = ?")
public class Profile {
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(updatable = false, nullable = false, name = "user_id")
    private Long userID;
    private String address;
    private String telephone;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;
    @Column(name = "deleted")
    private boolean isDeleted;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return userID.equals(profile.userID) &&
                address.equals(profile.address) &&
                telephone.equals(profile.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, address, telephone);
    }
}
