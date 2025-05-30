package main;

import java.util.*;
import items.*;

public class Store {
    private GamePanel gp;
    private StoreManager storeManager;
    private String currentCategory = "Seeds";
    private int currentItemIndex = 0;
    private int currentCategoryIndex = 0;
    private List<String> categories;
    private int gridCols = 4;
    
    public Store(GamePanel gp) {
        this.gp = gp;
        this.storeManager = new StoreManager();
        this.storeManager.initializeStores(gp);
        Set<String> storeTypes = storeManager.getStoreTypes();
        this.categories = new ArrayList<>(storeTypes);
        Collections.sort(categories);
        if (!categories.isEmpty()) {
            this.currentCategory = categories.get(0);
        }
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public List<? extends Item> getCurrentCategoryItems() {
        List<? extends Item> allItems = storeManager.getStoreByType(currentCategory);
        if (allItems == null || allItems.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> purchasableItems = new ArrayList<>();
        for (Item item : allItems) {
            if (item instanceof buysellable) {
                buysellable buyableItem = (buysellable) item;
                int buyPrice = buyableItem.getHargaBeli();
                if (buyPrice > 0) {
                    purchasableItems.add(item);
                }
            }
        }
        return purchasableItems;
    }

    public Item getCurrentItem() {
        List<? extends Item> items = getCurrentCategoryItems();
        if (items != null && !items.isEmpty()) {
            return items.get(currentItemIndex);
        }
        return null;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    public void setCurrentItemIndex(int index) {
        List<? extends Item> items = getCurrentCategoryItems();
        if (index >= 0 && index < items.size()) {
            this.currentItemIndex = index;
        } else {
            this.currentItemIndex = 0;
        }
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getCurrentCategoryIndex() {
        return currentCategoryIndex;
    }

    public void setCurrentCategoryIndex(int index) {
        if (index >= 0 && index < categories.size()) {
            this.currentCategoryIndex = index;
            this.currentCategory = categories.get(index);
            this.currentItemIndex = 0;
        } else {
            this.currentCategoryIndex = 0;
            this.currentCategory = categories.get(0);
        }
    }

    public void nextItem() {
        List<? extends Item> items = getCurrentCategoryItems();
        if (items != null && !items.isEmpty()) {
            int currentRow = currentItemIndex / gridCols;
            int currentCol = currentItemIndex % gridCols;
            int newRow = currentRow - 1;
            if (newRow < 0) {
                return;
            }
            int newIndex = newRow * gridCols + currentCol;
            if (newIndex >= items.size()) {
                for (int row = newRow; row >= 0; row--) {
                    int testIndex = row * gridCols + currentCol;
                    if (testIndex < items.size()) {
                        newIndex = testIndex;
                        break;
                    }
                }
            }
            currentItemIndex = newIndex;
        }
    }

    public void prevItem() {
        List<? extends Item> items = getCurrentCategoryItems();
        if (items != null && !items.isEmpty()) {
            int currentRow = currentItemIndex / gridCols;
            int currentCol = currentItemIndex % gridCols;
            int totalRows = (items.size() - 1) / gridCols;
            int newRow = currentRow + 1;
            if (newRow > totalRows) {
                return;
            }
            int newIndex = newRow * gridCols + currentCol;
            if (newIndex >= items.size()) {
                return;
            }
            currentItemIndex = newIndex;
        }
    }

    public void nextItemInRow() {
        List<? extends Item> items = getCurrentCategoryItems();
        if (items != null && !items.isEmpty()) {
            int currentRow = currentItemIndex / gridCols;
            int currentCol = currentItemIndex % gridCols;
            int newCol = currentCol + 1;
            int newIndex = currentRow * gridCols + newCol;
            if (newCol >= gridCols || newIndex >= items.size()) {
                return;
            }
            currentItemIndex = newIndex;
        }
    }

    public void prevItemInRow() {
        List<? extends Item> items = getCurrentCategoryItems();
        if (items != null && !items.isEmpty()) {
            int currentRow = currentItemIndex / gridCols;
            int currentCol = currentItemIndex % gridCols;
            int newCol = currentCol - 1;
            if (newCol < 0) {
                return;
            }
            int newIndex = currentRow * gridCols + newCol;
            currentItemIndex = newIndex;
        }
    }

    public void nextCategory() {
        currentCategoryIndex = (currentCategoryIndex + 1) % categories.size();
        currentCategory = categories.get(currentCategoryIndex);
        currentItemIndex = 0;
    }

    public void prevCategory() {
        currentCategoryIndex = (currentCategoryIndex - 1 + categories.size()) % categories.size();
        currentCategory = categories.get(currentCategoryIndex);
        currentItemIndex = 0;
    }

    public boolean buyItem(Item item) {
        if (item instanceof buysellable) {
            buysellable buyableItem = (buysellable) item;
            if (gp.player.getGold() >= buyableItem.getHargaBeli()) {
                gp.player.setGold(gp.player.getGold() - buyableItem.getHargaBeli());
                gp.player.addItemToInventory(item,1);
                return true;
            }
        }
        return false;
    }
}
