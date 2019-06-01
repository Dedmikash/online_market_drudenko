package com.gmail.dedmikash.market.service.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class ItemDTO {
    private Long id;
    private String name;
    private String uniqueNumber;
    private String price;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    @XmlElement(name = "unique_number")
    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getPrice() {
        return price;
    }

    @XmlElement(name = "price")
    public void setPrice(String price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    @XmlElement(name = "text") //TODO description -> text
    public void setText(String text) {
        this.text = text;
    }
}
