package com.campus.lostfound.service;

import com.campus.lostfound.exception.ResourceNotFoundException;
import com.campus.lostfound.model.Item;
import com.campus.lostfound.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String STATUS_CLAIMED = "CLAIMED";
    private static final String STATUS_NOT_CLAIMED = "NOT_CLAIMED";

    private static final String TYPE_LOST = "LOST";
    private static final String TYPE_FOUND = "FOUND";

    private final ItemRepository itemRepository;

    @Override
    public Item addItem(Item item) {
        validateType(item.getType());

        if (!StringUtils.hasText(item.getStatus())) {
            item.setStatus(STATUS_NOT_CLAIMED);
        } else {
            validateStatus(item.getStatus());
            item.setStatus(normalize(item.getStatus()));
        }

        item.setType(normalize(item.getType()));
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item updateItemStatus(Long id, String status) {
        validateStatus(status);

        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        existing.setStatus(normalize(status));
        return itemRepository.save(existing);
    }

    @Override
    public void deleteItem(Long id) {
        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        itemRepository.delete(existing);
    }

    @Override
    public List<Item> searchItems(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return itemRepository.findAll();
        }
        return itemRepository.findByDescriptionContainingIgnoreCase(keyword.trim());
    }

    private void validateType(String type) {
        String normalized = normalize(type);
        if (!TYPE_LOST.equals(normalized) && !TYPE_FOUND.equals(normalized)) {
            throw new IllegalArgumentException("Invalid item type. Allowed values: LOST, FOUND");
        }
    }

    private void validateStatus(String status) {
        String normalized = normalize(status);
        if (!STATUS_CLAIMED.equals(normalized) && !STATUS_NOT_CLAIMED.equals(normalized)) {
            throw new IllegalArgumentException("Invalid item status. Allowed values: CLAIMED, NOT_CLAIMED");
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }
}
