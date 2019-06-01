package com.gmail.dedmikash.market.service.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "catalog")
public class ItemCatalogXML {
    private List<ItemDTO> catalog;

    public List<ItemDTO> getCatalog() {
        return catalog;
    }

    @XmlElement(name = "item")
    public void setCatalog(List<ItemDTO> catalog) {
        this.catalog = catalog;
    }
}
