package com.example.InventoryManagementSystem.dao;

import com.example.InventoryManagementSystem.entities.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<Container, Integer> {
}
