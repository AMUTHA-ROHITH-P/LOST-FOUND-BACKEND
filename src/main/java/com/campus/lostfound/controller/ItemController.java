package com.campus.lostfound.controller;

import com.campus.lostfound.model.Item;
import com.campus.lostfound.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item addItem(@Valid @RequestBody CreateItemRequest request) {
        Item item = Item.builder()
                .type(request.getType())
                .description(request.getDescription())
                .location(request.getLocation())
                .contactInfo(request.getContactInfo())
                .status(request.getStatus())
                .build();
        return itemService.addItem(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping("/{id}")
    public Item updateItemStatus(@PathVariable Long id, @Valid @RequestBody UpdateItemStatusRequest request) {
        return itemService.updateItemStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam(required = false) String keyword) {
        return itemService.searchItems(keyword);
    }

    @Getter
    @Setter
    public static class CreateItemRequest {

        @NotBlank(message = "type is required")
        private String type;

        @NotBlank(message = "description is required")
        private String description;

        @NotBlank(message = "location is required")
        private String location;

        @NotBlank(message = "contactInfo is required")
        private String contactInfo;

        private String status;
    }

    @Getter
    @Setter
    public static class UpdateItemStatusRequest {

        @NotBlank(message = "status is required")
        private String status;
    }
}
