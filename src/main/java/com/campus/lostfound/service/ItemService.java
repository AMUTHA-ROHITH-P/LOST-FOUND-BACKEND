package com.campus.lostfound.service;

import com.campus.lostfound.model.Item;
import java.util.List;

public interface ItemService {

    Item addItem(Item item);

    List<Item> getAllItems();

    Item updateItemStatus(Long id, String status);

    void deleteItem(Long id);

    List<Item> searchItems(String keyword);
}
