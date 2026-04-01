package com.campus.lostfound.repository;

import com.campus.lostfound.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByDescriptionContainingIgnoreCase(String keyword);
}
