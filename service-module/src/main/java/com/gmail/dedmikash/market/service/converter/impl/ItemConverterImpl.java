package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.service.converter.ItemConverter;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toDTO(Item item) {
        if (item != null) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setUniqueNumber(item.getUniqueNumber());
            itemDTO.setPrice(item.getPrice().toString());
            itemDTO.setText(item.getText());
            itemDTO.setDeleted(item.isDeleted());
            return itemDTO;
        } else return null;
    }

    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setPrice(new BigDecimal(itemDTO.getPrice()));
        item.setDeleted(itemDTO.isDeleted());
        item.setText(itemDTO.getText());
        return item;
    }
}
