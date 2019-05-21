package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ItemRepository;
import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.service.ItemService;
import com.gmail.dedmikash.market.service.converter.ItemConverter;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setUniqueNumber(UUID.randomUUID().toString());
        itemRepository.create(itemConverter.fromDTO(itemDTO));
    }

    @Override
    @Transactional
    public ItemDTO getItemById(Long id) {
        return itemConverter.toDTO(itemRepository.findById(id));
    }

    @Override
    @Transactional
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        Item item = itemRepository.findById(id);
        if (item != null && !item.isDeleted()) {
            itemRepository.delete(item);
        }
    }

    @Override
    public PageDTO<ItemDTO> getItems(int page) {
        PageDTO<ItemDTO> items = new PageDTO<>();
        List<ItemDTO> itemDTOS = getPageOfItems(page);
        items.setList(itemDTOS);
        items.setCountOfPages(itemRepository.getCountOfPages());
        return items;
    }

    private List<ItemDTO> getPageOfItems(int page) {
        return itemRepository.getItems(page)
                .stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }
}
