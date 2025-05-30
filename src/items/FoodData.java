package items;

import main.GamePanel;
import java.util.*;

public class FoodData {
    private static food createFood(GamePanel gp, String name, int energi, int hargabeli, int hargajual) {
        return new food(name, gp, energi, hargabeli, hargajual);
    }
    
    public static List<food> getAllFoods(GamePanel gp) {
        List<food> list = new ArrayList<>();

        list.add(createFood(gp, "Fish n' Chips", 50, 150, 135));
        list.add(createFood(gp, "Baguette", 25, 100, 80));
        list.add(createFood(gp, "Sashimi", 70, 300, 275));
        list.add(createFood(gp, "Fugu", 50, 0, 135));
        list.add(createFood(gp, "Wine", 20, 100, 90));
        list.add(createFood(gp, "Pumpkin Pie", 35, 120, 100));
        list.add(createFood(gp, "Veggie Soup", 40, 140, 120));
        list.add(createFood(gp, "Fish Stew", 70, 280, 260));
        list.add(createFood(gp, "Spakbor Salad", 70, 0, 250));
        list.add(createFood(gp, "Fish Sandwich", 50, 200, 180));
        list.add(createFood(gp, "The Legends of Spakbor", 100, 0, 2000));
        list.add(createFood(gp, "Cooked Pig's Head", 100, 1000, 0));

        System.out.println("Created " + list.size() + " foods successfully");
        return list;
    }

    public static food getFoodByName(GamePanel gp, String foodName) {
        List<food> allFoods = getAllFoods(gp);
        
        for (food f : allFoods) {
            if (f.getName().equalsIgnoreCase(foodName)) {
                return f;
            }
        }
        
        return null;
    }
}