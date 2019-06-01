package com.gmail.dedmikash.market.web.rest;

import com.gmail.dedmikash.market.service.ItemService;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.web.validator.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemAPIController {
    private static final Logger logger = LoggerFactory.getLogger(ItemAPIController.class);
    private final ItemService itemService;
    private final ItemValidator itemValidator;

    public ItemAPIController(ItemService itemService, ItemValidator itemValidator) {
        this.itemService = itemService;
        this.itemValidator = itemValidator;
    }

    @GetMapping
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity showItems() {
        List<ItemDTO> itemDTOList = itemService.getAllItems();
        logger.info("All items were shown with REST API");
        return new ResponseEntity(itemDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity showItemWithId(@PathVariable("id") Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        if (itemDTO != null) {
            logger.info("Item with id: {} - was shown with REST API", id);
            return new ResponseEntity(itemDTO, HttpStatus.OK);
        } else {
            logger.info("Item with id: {} - wasn't shown with REST API. No such item or it was soft deleted", id);
            return new ResponseEntity("No item with such id in DB or it was soft deleted", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity saveItem(@RequestBody ItemDTO itemDTO, BindingResult result) {
        itemValidator.validate(itemDTO, result);
        if (result.hasErrors()) {
            return new ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST);
        }
        itemService.saveItem(itemDTO);
        logger.info("Added item: {} - with REST API", itemDTO.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);
        logger.info("Item with id: {} -was soft deleted with REST API");
        return new ResponseEntity(HttpStatus.OK);
    }
}
