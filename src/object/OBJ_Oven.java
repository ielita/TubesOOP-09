package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import items.FoodData;
import items.Item;
import items.Recipe;
import items.fish;
import items.food;
import main.GamePanel;
import main.InventoryManager;

public class OBJ_Oven extends SuperObject {

    public GamePanel gp;
    public List<Recipe> recipes;
    public List<food> foodList;
    private InventoryManager inventory;
    private int coalUseCount = 0;

    public class CookingJob {
        Recipe recipe;
        int startHour;
        int startMinute;
        int durationInMinutes;

        CookingJob(Recipe recipe, int startHour, int startMinute, int durationInMinutes) {
            this.recipe = recipe;
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.durationInMinutes = durationInMinutes;
        }
    }

    public OBJ_Oven(GamePanel gp) {
        this.gp = gp;
        this.keyH = gp.keyH;
        name = "Oven";
        this.inventory = gp.player.inventoryManager;
        this.recipes = gp.player.recipesUnlocked;
        this.foodList = FoodData.getAllFoods(gp);

        try {
            image = ImageIO.read(new File("res/objects/oven.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
        solidArea = new Rectangle(0, 0, 48, 48);
        coalUseCount = 0; 
    }

    @Override
    protected void onInteract() {
        if (collision) {
            gp.gameState = gp.cookingState;
            gp.keyH.cursorIndex = 0; // Reset cursor index
            collision = false;
            return;
        }
    }

    // Ambil resep yang sudah unlocked (cek syarat unlock di sini)
    public List<Recipe> getUnlockedRecipes() {
        List<Recipe> unlocked = new ArrayList<>();
        for (Recipe r : recipes) {
            if (isRecipeUnlocked(r)) unlocked.add(r);
        }
        return unlocked;
    }

    // Cek syarat unlock resep
    public boolean isRecipeUnlocked(Recipe r) {
        switch (r.getUnlockType()) {
            case DEFAULT:
                return true;
            case STORE:
                return r.isUnlocked(); // unlock manual via store
            case FISH_COUNT:
                return gp.player.getTotalFishCaught() >= Integer.parseInt(r.getUnlockParam());
            case ITEM_OBTAIN:
                return inventory.hasItem(r.getUnlockParam());
            case HARVEST:
                return gp.player.getTotalHarvest() > 0;
            case FISH_SPECIFIC:
                return inventory.hasItem(r.getUnlockParam());
            default:
                return false;
        }
    }

    public int getCoalUseCount() {
        return coalUseCount;
    }

    public void setCoalUseCount(int count) {
        this.coalUseCount = count;
    }

    // Cek bahan (termasuk Any Fish)
    public boolean canCook(Recipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            if (name.equals("Any Fish")) {
                for (Item fish : inventory.getInventory().keySet()) {
                    if (fish instanceof fish) {
                        if (inventory.getItemQuantity(fish.getName()) >= qty) {
                            return true; 
                        }
                    }
                }
                return false;
            } else {
                if (inventory.getItemQuantity(name) < qty) 
                    {
                        return false; 
                    }
                
            }
        }
           
        return true;
    }

    // Cek fuel
    public boolean hasFuel() {
        return inventory.getItemQuantity("Firewood") > 0 || inventory.getItemQuantity("Coal") > 0;
    }

    public void cook(Recipe recipe, String fuel) {
        if (!canCook(recipe) || !hasFuel() || gp.ovenCookingJob != null) return;

        // kurangi bahan
        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            if (name.equals("Any Fish")) {
                int sisa = qty;
                for (Item fish : new ArrayList<>(inventory.getInventory().keySet())) {
                    if (fish instanceof fish) {
                        int fishQty = inventory.getItemQuantity(fish.getName());
                        int toRemove = Math.min(fishQty, sisa);
                        inventory.removeItem(fish, toRemove);
                        sisa -= toRemove;
                        if (sisa <= 0) break;
                    }
                }
            } else {
                inventory.removeItem(inventory.findItemByName(name), qty);
            }
        }

        // fuel
        if (fuel.equals("Firewood")) {
            inventory.removeItem(inventory.findItemByName("Firewood"), 1);
        } else if (fuel.equals("Coal")) {
            coalUseCount++;
            if (coalUseCount >= 2) {
                inventory.removeItem(inventory.findItemByName("Coal"), 1);
                coalUseCount = 0;
            }
            gp.player.setCoalUseCount(coalUseCount);
        }

        gp.player.reduceEnergy(10);
        int currentHour = gp.timeM.getHour();
        int currentMinute = gp.timeM.getMinute();
        gp.ovenCookingJob = new CookingJob(recipe, currentHour, currentMinute, 60); 
    }

    public void update() {
        if (gp.ovenCookingJob != null) {
            int currentHour = gp.timeM.getHour();
            int currentMinute = gp.timeM.getMinute();

            int startTotalMinutes = gp.ovenCookingJob.startHour * 60 + gp.ovenCookingJob.startMinute;
            int currentTotalMinutes = currentHour * 60 + currentMinute;

            int elapsedMinutes = currentTotalMinutes - startTotalMinutes;
            if (elapsedMinutes < 0) elapsedMinutes += 24 * 60; // handling lewat tengah malam

            if (elapsedMinutes >= gp.ovenCookingJob.durationInMinutes) {
                // Masak selesai, beri makanan ke inventory
                for (food f : foodList) {
                    if (f.getName().equals(gp.ovenCookingJob.recipe.getName())) {
                        inventory.addItem(f, 1);
                        break;
                    }
                }
                gp.ovenCookingJob = null;
            }
        }
    }

    public List<String> getAvailableFuels() {
        List<String> fuels = new ArrayList<>();
        if (inventory.getItemQuantity("Firewood") > 0) fuels.add("Firewood");
        if (inventory.getItemQuantity("Coal") > 0) fuels.add("Coal");
        return fuels;
    }
    
}