package com.gmail.dedmikash.market.repository.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SQLDelete(sql = "UPDATE item SET deleted = 1 WHERE id = ?")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String name;
    @Column(name = "unique_number")
    private String uniqueNumber;
    private BigDecimal price;
    private String text;
    @OneToMany(mappedBy = "item", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @Column(name = "deleted")
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

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(uniqueNumber, item.uniqueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, uniqueNumber);
    }
}
