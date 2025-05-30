package main;

import java.util.*;
import items.*;

public class StoreManager {
    private List<seed> seedStore;
    private List<crop> cropStore;
    private List<food> foodStore;
    private List<Item> miscStore;
    private List<Recipe> recipes;
    private Map<String, List<? extends Item>> allStore;

    public StoreManager() {
        seedStore = new ArrayList<>();
        cropStore = new ArrayList<>();
        foodStore = new ArrayList<>();
        recipes = new ArrayList<>();
        miscStore = new ArrayList<>();

        allStore = new HashMap<>();
        allStore.put("Seeds", seedStore);
        allStore.put("Crops", cropStore);
        allStore.put("Food", foodStore);
        allStore.put("Misc", miscStore);
        allStore.put("Recipes", recipes);
    }

    public void initializeStores(GamePanel gp) {
        seedStore.addAll(SeedData.getAllSeeds(gp));
        cropStore.addAll(CropData.getAllCrops(gp));
        foodStore.addAll(FoodData.getAllFoods(gp));
        miscStore.addAll(MiscData.getAllMisc(gp));
        List<Recipe> tempRecipes = gp.player.recipesUnlocked;
        for (Recipe recipe : tempRecipes) {
            if (recipe.getUnlockType() == Recipe.UnlockType.STORE) {
                recipes.add(recipe);
            }
        }
    }

    public List<seed> getSeedStore() {
        return seedStore;
    }
    public List<crop> getCropStore() {
        return cropStore;
    }
    public List<food> getFoodStore() {
        return foodStore;
    }
    public List<Item> getMiscStore() {
        return miscStore;
    }

    public Map<String, List<? extends Item>> getAllStore() {
        return allStore;
    }

    public Set<String> getStoreTypes() {
        return allStore.keySet();
    }
    public List<? extends Item> getStoreByType(String type) {
        return allStore.getOrDefault(type, Collections.emptyList());
    }
    public void addItemToStore(String type, Item item) {
        switch (type) {
            case "Seeds":
                if (item instanceof seed) {
                    seedStore.add((seed) item);
                }
                break;
            case "Crops":
                if (item instanceof crop) {
                    cropStore.add((crop) item);
                }
                break;
            case "Food":
                if (item instanceof food) {
                    foodStore.add((food) item);
                }
                break;
            case "Misc":
                miscStore.add(item);
                break;
            default:
                break;
        }
    }
    public void removeItemFromStore(String type, Item item) {
        switch (type) {
            case "Seeds":
                seedStore.remove(item);
                break;
            case "Crops":
                cropStore.remove(item);
                break;
            case "Food":
                foodStore.remove(item);
                break;
            case "Misc":
                miscStore.remove(item);
                break;
            case "Recipes":
                recipes.remove(item);
                break;
            default:
                break;
        }
    }    

}