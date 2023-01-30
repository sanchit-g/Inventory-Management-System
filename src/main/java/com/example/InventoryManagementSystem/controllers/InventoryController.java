package com.example.InventoryManagementSystem.controllers;

import com.example.InventoryManagementSystem.entities.Item;
import com.example.InventoryManagementSystem.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return this.itemService.getAllItems();
    }

    @PostMapping("/items")
    public void addItem(@RequestBody Item item) {
        this.itemService.addItem(item);
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable int id) {
        return this.itemService.getItemById(id);
    }

    @PutMapping("/items")
    public void updateItem(Item item) {
        this.itemService.updateItem(item);
    }

    @DeleteMapping("/items")
    public void deleteItem(int id) {
        this.itemService.deleteItem(id);
    }
}