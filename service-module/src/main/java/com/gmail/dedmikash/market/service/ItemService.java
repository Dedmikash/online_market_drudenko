package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.DownloadingItemsResult;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;

import java.io.InputStream;
import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    ItemDTO getItemById(Long id);

    List<ItemDTO> getAllItems();

    void deleteItemById(Long id);

    PageDTO<ItemDTO> getItems(int page);

    DownloadingItemsResult uploadItemsFromXML(InputStream xmlInputStream);
}
