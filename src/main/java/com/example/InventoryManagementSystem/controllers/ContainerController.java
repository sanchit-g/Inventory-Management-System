package com.example.InventoryManagementSystem.controllers;

import com.example.InventoryManagementSystem.entities.Container;
import com.example.InventoryManagementSystem.services.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    @GetMapping("/containers")
    public List<Container> getAllContainers() {
        return this.containerService.getAllContainers();
    }

    @GetMapping("/containers/{id}")
    public Container getContainerById(@PathVariable int id) {
        return this.containerService.getContainerById(id);
    }

    @PostMapping("/containers")
    public void addContainer(@RequestBody Container container) {
        this.containerService.addContainer(container);
    }

    @PutMapping("/containers")
    public void updateContainer(Container container) {
        this.containerService.updateContainer(container);
    }

    @DeleteMapping("/containers/{id}")
    public void deleteContainer(@PathVariable int id) {
        this.containerService.deleteContainer(id);
    }
}
