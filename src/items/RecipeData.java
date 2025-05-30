package items;

import java.util.*;
import items.Recipe;
import main.GamePanel;


public class RecipeData {
    public static List<Recipe> getAllRecipes(GamePanel gp) {
        List<Recipe> list = new ArrayList<>();

        list.add(new Recipe(
            "recipe_1", "Fish n’ Chips",
            Map.of("Any Fish", 2, "Wheat", 1, "Potato", 1),
            Recipe.UnlockType.STORE, "", false
        , gp));
        list.add(new Recipe(
            "recipe_2", "Baguette",
            Map.of("Wheat", 3),
            Recipe.UnlockType.DEFAULT, "", true, gp
        ));
        list.add(new Recipe(
            "recipe_3", "Sashimi",
            Map.of("Salmon", 3),
            Recipe.UnlockType.FISH_COUNT, "10", false, gp
        ));
        list.add(new Recipe(
            "recipe_4", "Fugu",
            Map.of("Pufferfish", 1),
            Recipe.UnlockType.FISH_SPECIFIC, "Pufferfish", false, gp
        ));
        list.add(new Recipe(
            "recipe_5", "Wine",
            Map.of("Grape", 2),
            Recipe.UnlockType.DEFAULT, "", true, gp
        ));
        list.add(new Recipe(
            "recipe_6", "Pumpkin Pie",
            Map.of("Egg", 1, "Wheat", 1, "Pumpkin", 1),
            Recipe.UnlockType.DEFAULT, "", true, gp
        ));
        list.add(new Recipe(
            "recipe_7", "Veggie Soup",
            Map.of("Cauliflower", 1, "Parsnip", 1, "Potato", 1, "Tomato", 1),
            Recipe.UnlockType.HARVEST, "", false, gp
        ));
        list.add(new Recipe(
            "recipe_8", "Fish Stew",
            Map.of("Any Fish", 2, "Hot Pepper", 1, "Cauliflower", 2),
            Recipe.UnlockType.ITEM_OBTAIN, "Hot Pepper", false, gp
        ));
        list.add(new Recipe(
            "recipe_9", "Spakbor Salad",
            Map.of("Melon", 1, "Cranberry", 1, "Blueberry", 1, "Tomato", 1),
            Recipe.UnlockType.DEFAULT, "", true, gp
        ));
        list.add(new Recipe(
            "recipe_10", "Fish Sandwich",
            Map.of("Any Fish", 1, "Wheat", 2, "Tomato", 1, "Hot Pepper", 1),
            Recipe.UnlockType.STORE, "", false, gp
        ));
        list.add(new Recipe(
            "recipe_11", "The Legends of Spakbor",
            Map.of("Legend fish", 1, "Potato", 2, "Parsnip", 1, "Tomato", 1, "Eggplant", 1),
            Recipe.UnlockType.FISH_SPECIFIC, "Legend", false, gp
        ));

        return list;
    }
}