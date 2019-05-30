package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ItemService;
import com.gmail.dedmikash.market.service.OrderService;
import com.gmail.dedmikash.market.service.model.AppUserPrincipal;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.web.validator.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/items")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final ItemValidator itemValidator;
    private final OrderService orderService;

    public ItemController(ItemService itemService,
                          ItemValidator itemValidator,
                          OrderService orderService) {
        this.itemService = itemService;
        this.itemValidator = itemValidator;
        this.orderService = orderService;
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

    @GetMapping("/{item_id}/copy")
    public String copyItemById(@PathVariable(name = "item_id") Long itemId,
                               ItemDTO itemDTO,
                               Model model) {
        model.addAttribute("item", itemService.getItemById(itemId));
        return "copyitem";
    }

    @PostMapping("/{item_id}/copy")
    public String createCopyItem(@PathVariable(name = "item_id") Long itemId,
                                 @ModelAttribute ItemDTO itemDTO,
                                 BindingResult result,
                                 Model model) {
        model.addAttribute("item", itemService.getItemById(itemId));
        itemValidator.validate(itemDTO, result);
        if (result.hasErrors()) {
            model.addAttribute("item", itemService.getItemById(itemId));
            return "copyitem";
        }
        itemService.saveItem(itemDTO);
        logger.info("Save item: {}", itemDTO);
        return "redirect:/items";
    }

    @PostMapping("/order")
    public String createCopyItem(@RequestParam(name = "item_id") Long itemId,
                                 @RequestParam(name = "quantity") int quantity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = ((AppUserPrincipal) userDetails).getId();
        orderService.createOrder(id, itemId, quantity);
        logger.info("Created order: itemId - {}, quantity - {}, userID - {}", itemId, quantity, id);
        return "redirect:/orders";
    }
}
