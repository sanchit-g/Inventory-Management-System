package com.example.InventoryManagementSystem.services;

import com.example.InventoryManagementSystem.dao.ItemRepository;
import com.example.InventoryManagementSystem.entities.Container;
import com.example.InventoryManagementSystem.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemServiceImpl implements ItemService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ContainerService containerService;

    @Autowired
    public ItemServiceImpl(JdbcTemplate jdbcTemplate, ContainerService containerService) {
        this.jdbcTemplate = jdbcTemplate;
        this.containerService = containerService;
    }

    @Override
    public List<Item> getAllItems() {
        String query = "SELECT * FROM items i INNER JOIN item_containers ic ON i.id = ic.item_id";
        return this.jdbcTemplate.query(query, rs -> {
            Map<Integer, Item> map = new HashMap<>();
            while (rs.next()) {
                if (map.containsKey(rs.getInt("id"))) {
                    map.get(rs.getInt("id")).getContainers().add(rs.getInt("containers"));
                } else {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getInt("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setExpiration_date(rs.getDate("expiration_date"));
                    List<Integer> containersArray = new ArrayList<>();
                    containersArray.add(rs.getInt("containers"));
                    item.setContainers(containersArray);
                    map.put(item.getId(), item);
                }
            }

            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int addItem(Item item) {
        int check = checkItem(item);
        if(check != -1) {
            Item existingItem = getItemById(check);
            int totalContainers = existingItem.getContainers().size();

            if(this.containerService.spaceAvailable(existingItem.getContainers().get(totalContainers - 1), item.getQuantity()) == Boolean.TRUE) {
                Container container = this.containerService.getContainerById(existingItem.getContainers().get(totalContainers - 1));
                container.setCurrentQuantity(container.getCurrentQuantity() + item.getQuantity());
                this.containerService.updateContainer(container);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                this.itemRepository.save(existingItem);
                return container.getContainerId();
            }

            else {
                Container container = new Container();
                container.setMaxCapacity(100);
                container.setCurrentQuantity(item.getQuantity());
                container.setItemId(item.getId());
                this.containerService.addContainer(container);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                existingItem.getContainers().add(container.getContainerId());
                this.itemRepository.save(existingItem);
                return container.getContainerId();
            }
        }
        else {
            Container container = new Container();
            container.setMaxCapacity(100);
            container.setCurrentQuantity(item.getQuantity());
            container.setItemId(item.getId());
            this.containerService.addContainer(container);
            item.getContainers().add(container.getContainerId());
            this.itemRepository.save(item);
            return container.getContainerId();
        }
    }

    @Override
    public Item getItemById(int id) {
        String query = "SELECT * FROM items i INNER JOIN item_containers ic ON i.id = ic.item_id WHERE id = ?";
        List<Item> itemList = this.jdbcTemplate.query(query, rs -> {
            Map<Integer, Item> map = new HashMap<>();
            while (rs.next()) {
                if (map.containsKey(rs.getInt("id"))) {
                    map.get(rs.getInt("id")).getContainers().add(rs.getInt("containers"));
                } else {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getInt("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setExpiration_date(rs.getDate("expiration_date"));
                    List<Integer> containersArray = new ArrayList<>();
                    containersArray.add(rs.getInt("containers"));
                    item.setContainers(containersArray);
                    map.put(item.getId(), item);
                }
            }
            return new ArrayList<>(map.values());
        }, id);

        return itemList.get(0);
    }

    @Override
    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void updateItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public int checkItem(Item temp) {
        String query = "SELECT * FROM items i INNER JOIN item_containers ic ON i.id = ic.item_id WHERE i.name = ? AND i.price = ? AND i.expiration_date = ?";
        List<Item> itemList = this.jdbcTemplate.query(query, rs -> {
            Map<Integer, Item> map = new HashMap<>();
            while (rs.next()) {
                if (map.containsKey(rs.getInt("id"))) {
                    map.get(rs.getInt("id")).getContainers().add(rs.getInt("containers"));
                } else {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getInt("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setExpiration_date(rs.getDate("expiration_date"));
                    List<Integer> containersArray = new ArrayList<>();
                    containersArray.add(rs.getInt("containers"));
                    item.setContainers(containersArray);
                    map.put(item.getId(), item);
                }
            }
            return new ArrayList<>(map.values());
        }, temp.getName(), temp.getPrice(), temp.getExpiration_date());

        if(itemList.size() == 0) {
            return -1;
        } else {
            return itemList.get(0).getId();
        }
    }
}
