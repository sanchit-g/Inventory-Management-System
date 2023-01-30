package com.example.InventoryManagementSystem.services;

import com.example.InventoryManagementSystem.dao.ContainerRepository;
import com.example.InventoryManagementSystem.entities.Container;
import com.example.InventoryManagementSystem.mappers.ContainerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addContainer(Container container) {
        containerRepository.save(container);
    }

    @Override
    public void updateContainer(Container container) {
        containerRepository.save(container);
    }

    @Override
    public void deleteContainer(int id) {
        containerRepository.deleteById(id);
    }

    @Override
    public List<Container> getAllContainers() {
        String query = "SELECT * FROM containers";
        RowMapper<Container> containerRowMapper = new ContainerMapper();
        return jdbcTemplate.query(query, containerRowMapper);
    }

    @Override
    public Container getContainerById(int id) {
        return containerRepository.getReferenceById(id);
    }

    @Override
    public Boolean spaceAvailable(int containerId, int requiredQuantity) {
        Container container = getContainerById(containerId);
        if(container.getMaxCapacity() - container.getCurrentQuantity() >= requiredQuantity) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
