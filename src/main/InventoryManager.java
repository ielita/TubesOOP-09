package main;

import java.util.Map;
import java.util.HashMap;
import items.Item;

public class InventoryManager {
    private Map<Item, Integer> inventory;
    private Item onhandItem;
    private static final int MAX_INVENTORY_SIZE = 32; // Example limit, adjust as needed

    public InventoryManager() {
        inventory = new HashMap<>();
        onhandItem = null;
    }

    public void addItem(Item item, int quantity) {
        if (item == null || quantity <= 0) return;
        
        // Check if item with same name already exists
        Item existingItem = findItemByName(item.getName());
        
        if (existingItem != null) {
            // Item already exists, add to existing quantity
            int currentQuantity = inventory.get(existingItem);
            inventory.put(existingItem, currentQuantity + quantity);
            System.out.println("Added " + quantity + " " + item.getName() + " (Total: " + (currentQuantity + quantity) + ")");
        } else {
            if (inventory.size() >= MAX_INVENTORY_SIZE) {
                System.out.println("Inventory full! Cannot add " + item.getName());
                return; 
            }
            // New item, add to inventory
            inventory.put(item, quantity);
            System.out.println("Added " + quantity + " " + item.getName() + " (New item)");
        }
    }

    // Helper method to find item by name
    private Item findItemByName(String name) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item, int quantity) {
        if (item == null || quantity <= 0) return;
        
        // Find item by name (in case it's a different instance)
        Item existingItem = findItemByName(item.getName());
        
        if (existingItem != null && inventory.containsKey(existingItem)) {
            int currentQuantity = inventory.get(existingItem);
            
            if (currentQuantity <= quantity) {
                // Remove completely
                inventory.remove(existingItem);
                System.out.println("Removed all " + existingItem.getName());
                
                // Clear onhand if this was the onhand item
                if (onhandItem != null && onhandItem.getName().equals(existingItem.getName())) {
                    onhandItem = null;
                }
            } else {
                // Reduce quantity
                inventory.put(existingItem, currentQuantity - quantity);
                System.out.println("Removed " + quantity + " " + existingItem.getName() + " (Remaining: " + (currentQuantity - quantity) + ")");
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
        // Make sure the item exists in inventory
        if (item != null) {
            Item existingItem = findItemByName(item.getName());
            if (existingItem != null) {
                this.onhandItem = existingItem;
            } else {
                this.onhandItem = null;
                System.out.println("Cannot set onhand: " + item.getName() + " not in inventory");
            }
        } else {
            this.onhandItem = null;
        }
    }

    public boolean hasItem(String itemName) {
        return findItemByName(itemName) != null;
    }

    public int getItemQuantity(String itemName) {
        Item item = findItemByName(itemName);
        return item != null ? inventory.get(item) : 0;
    }
}
