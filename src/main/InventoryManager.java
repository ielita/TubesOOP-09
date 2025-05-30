package main;

import java.util.Map;
import java.util.HashMap;
import items.Item;
import items.fish;

public class InventoryManager {
    private Map<Item, Integer> inventory;
    private Item onhandItem;

    public InventoryManager() {
        inventory = new HashMap<>();
        onhandItem = null;
    }

    public void addItem(Item item, int quantity) {
        if (item == null || quantity <= 0) return;
        
        Item existingItem = findItemByName(item.getName());
        
        if (existingItem != null) {
            int currentQuantity = inventory.get(existingItem);
            inventory.put(existingItem, currentQuantity + quantity);
        } else {
            inventory.put(item, quantity);
        }
    }

    public Item findItemByName(String name) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item, int quantity) {
        if (item == null || quantity <= 0) return;
        
        Item existingItem = findItemByName(item.getName());
        
        if (existingItem != null && inventory.containsKey(existingItem)) {
            int currentQuantity = inventory.get(existingItem);
            
            if (currentQuantity <= quantity) {
                inventory.remove(existingItem);
                if (onhandItem != null && onhandItem.getName().equals(existingItem.getName())) {
                    onhandItem = null;
                }
            } else {
                inventory.put(existingItem, currentQuantity - quantity);
            }
        }
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public Item getOnhandItem() {
        return onhandItem;
    }

    public void setOnhandItem(Item item) {
        if (item != null) {
            Item existingItem = findItemByName(item.getName());
            if (existingItem != null) {
                this.onhandItem = existingItem;
            } else {
                this.onhandItem = null;
            }
        } else {
            this.onhandItem = null;
        }
    }

    public boolean hasItem(String itemName) {
        return findItemByName(itemName) != null;
    }

    public void removeFish(int quantity) {
        for (Item item : inventory.keySet()) {
            if (item instanceof fish) {
                int currentQuantity = inventory.get(item);
                if (currentQuantity >= quantity) {
                    inventory.put(item, currentQuantity - quantity);
                    if (currentQuantity - quantity <= 0) {
                        inventory.remove(item);
                    }
                    return;
                } else {
                    inventory.remove(item);
                    quantity -= currentQuantity;
                }
            }
        }
    }

    public int getItemQuantity(String itemName) {
        Item item = findItemByName(itemName);
        return item != null ? inventory.get(item) : 0;
    }
}
