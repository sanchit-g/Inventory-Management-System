package com.example.InventoryManagementSystem.services;

import com.example.InventoryManagementSystem.entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    int addItem(Item item);
    Item getItemById(int id);
    void deleteItem(int id);
    void updateItem(Item item);
    int checkItem(Item temp);
}
