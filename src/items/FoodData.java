package items;

import main.GamePanel;
import java.util.*;

public class FoodData {

    public static List<food> getAllFoods(GamePanel gp) {
        List<food> list = new ArrayList<>();

        // Direct constructor calls: name, gp, energi, hargabeli, hargajual
        list.add(new food("Fish n' Chips", gp, 50, 150, 135));
        list.add(new food("Baguette", gp, 25, 100, 80));
        list.add(new food("Sashimi", gp, 70, 300, 275));
        list.add(new food("Fugu", gp, 50, 0, 135));
        list.add(new food("Wine", gp, 20, 100, 90));
        list.add(new food("Pumpkin Pie", gp, 35, 120, 100));
        list.add(new food("Veggie Soup", gp, 40, 140, 120));
        list.add(new food("Fish Stew", gp, 70, 280, 260));
        list.add(new food("Spakbor Salad", gp, 70, 0, 250));
        list.add(new food("Fish Sandwich", gp, 50, 200, 180));
        list.add(new food("The Legends of Spakbor", gp, 100, 0, 2000));
        list.add(new food("Cooked Pig's Head", gp, 100, 1000, 0));

        return list;
    }

    // Get food by name
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