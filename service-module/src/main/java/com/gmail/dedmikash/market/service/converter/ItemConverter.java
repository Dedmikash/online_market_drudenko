package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.service.model.ItemDTO;

public interface ItemConverter {
    ItemDTO toDTO(Item item);

    Item fromDTO(ItemDTO itemDTO);
}
