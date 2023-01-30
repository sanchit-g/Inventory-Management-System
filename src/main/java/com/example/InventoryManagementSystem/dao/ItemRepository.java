package com.example.InventoryManagementSystem.dao;

import com.example.InventoryManagementSystem.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
