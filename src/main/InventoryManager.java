package main;

import java.util.Map;
import java.util.HashMap;
import items.Item;

public class InventoryManager {
    private Map<Item, Integer> inventory;
    private Item onhandItem;

    public InventoryManager() {
        inventory = new HashMap<>();
        onhandItem = null;
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public void addItem(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
        } else {
            inventory.put(item, quantity);
        }
    }

    public void removeItem(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            int currentQuantity = inventory.get(item);
            if (currentQuantity > quantity) {
                inventory.put(item, currentQuantity - quantity);
            } else {
                inventory.remove(item);
            }
        }
    }

    public Item getOnhandItem() {
        return onhandItem;
    }

    public void setOnhandItem(Item item) {
        this.onhandItem = item;
    }
}