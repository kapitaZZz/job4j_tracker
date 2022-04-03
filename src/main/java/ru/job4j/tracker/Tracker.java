package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
    private final List<Item> items = new ArrayList<>();
    private int ids = 0;
    private int size = 0;

    public Item add(Item item) {
        item.setId(ids++);
        items.add(item);
        return item;
    }

    public Item findById(int id) {
        int index = indexOf(id);
        return indexOf(id) != -1 ? items.get(index) : null;
    }

    private int indexOf(int id) {
        int result = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    public List<Item> findAll() {
        return List.copyOf(items);
    }

    public List<Item> findByName(String key) {
        List<Item> res = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                res.add(item);
            }
        }
        return res;
    }

    public boolean replace(int id, Item item) {
        int index = indexOf(id);
        boolean flag = index != -1;
        if (flag) {
            item.setId(id);
            items.set(index, item);
        }
        return flag;
    }

    public boolean delete(int id) {
        int index = indexOf(id);
        boolean flag = index != -1;
        if (flag) {
            items.remove(index);
        }
        return flag;
    }
}