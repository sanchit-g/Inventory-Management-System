package com.example.InventoryManagementSystem.services;

import com.example.InventoryManagementSystem.entities.Container;

import java.util.List;

public interface ContainerService {
    void addContainer(Container container);
    void updateContainer(Container container);
    void deleteContainer(int id);
    List<Container> getAllContainers();
    Container getContainerById(int id);
    Boolean spaceAvailable(int containerId, int quantity);
}
