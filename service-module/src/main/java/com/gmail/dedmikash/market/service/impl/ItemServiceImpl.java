package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ItemRepository;
import com.gmail.dedmikash.market.repository.model.Item;
import com.gmail.dedmikash.market.service.ItemService;
import com.gmail.dedmikash.market.service.converter.ItemConverter;
import com.gmail.dedmikash.market.service.model.DownloadingItemsResult;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.parser.JaxbParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final JaxbParser jaxbParser;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter,
                           JaxbParser jaxbParser) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.jaxbParser = jaxbParser;
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
    @Transactional
    public PageDTO<ItemDTO> getItems(int page) {
        PageDTO<ItemDTO> items = new PageDTO<>();
        List<ItemDTO> itemDTOS = getPageOfItems(page);
        items.setList(itemDTOS);
        items.setCountOfPages(itemRepository.getCountOfPages());
        return items;
    }

    @Override
    @Transactional
    public DownloadingItemsResult uploadItemsFromXML(InputStream xmlInputStream) {
        DownloadingItemsResult result = new DownloadingItemsResult();
        List<ItemDTO> itemDTOList = jaxbParser.parse(xmlInputStream);
        int counter = 0;
        int all = 0;
        for (ItemDTO itemDTO : itemDTOList) {
            if (itemRepository.findByUniqueNumber(itemDTO.getUniqueNumber()) == null
            ) {
                try {
                    itemRepository.create(itemConverter.fromDTO(itemDTO));
                } catch (Exception e) {
                    counter--;
                }
                counter++;
                all++;
            } else {
                all++;
            }
        }
        result.setCounter(counter);
        result.setAll(all);
        return result;
    }

    private List<ItemDTO> getPageOfItems(int page) {
        return itemRepository.getItems(page)
                .stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }
}
