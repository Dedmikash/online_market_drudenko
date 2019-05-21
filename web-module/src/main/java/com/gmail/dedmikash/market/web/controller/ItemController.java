package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ItemService;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/items")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String getItems(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           Model model) {
        PageDTO<ItemDTO> items = itemService.getItems(page);
        model.addAttribute("items", items.getList());
        model.addAttribute("pages", items.getCountOfPages());
        logger.info("Getting items {}, page {}", items.getList(), page);
        return "items";
    }

    @GetMapping("/{item_id}")
    public String getItemById(@PathVariable(name = "item_id") Long itemId,
                              Model model) {
        model.addAttribute("item", itemService.getItemById(itemId));
        logger.info("Getting item with id {}", itemId);
        return "item";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam(name = "item_id") Long itemId) {
        itemService.deleteItemById(itemId);
        logger.info("Deleting item with id: {}", itemId);
        return "redirect:/items";
    }
}
