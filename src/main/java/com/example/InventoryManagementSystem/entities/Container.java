package com.example.InventoryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "containers")
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Container {
    @Id
    @Column(name = "container_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int containerId;
    private int maxCapacity;
    private int currentQuantity;
    private int itemId;

    public Container() {
    }
}
