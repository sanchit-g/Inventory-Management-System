package com.example.InventoryManagementSystem.mappers;

import com.example.InventoryManagementSystem.entities.Container;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContainerMapper implements RowMapper<Container> {
    @Override
    public Container mapRow(ResultSet rs, int rowNum) throws SQLException {
        Container container = new Container();
        container.setContainerId(rs.getInt("container_id"));
        container.setItemId(rs.getInt("item_id"));
        container.setCurrentQuantity(rs.getInt("current_quantity"));
        container.setMaxCapacity(rs.getInt("max_capacity"));
        return container;
    }
}
