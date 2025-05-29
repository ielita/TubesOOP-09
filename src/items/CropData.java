package items;

import main.GamePanel;
import java.util.*;

public class CropData {
    
    // ========== FACTORY METHOD ==========
    private static crop createCrop(GamePanel gp, String name, int hargabeli, int hargajual, int jumlah) {
        return new crop(name, gp, hargabeli, hargajual, jumlah);
    }
    
    // ========== MAIN DATA METHOD ==========
    public static List<crop> getAllCrops(GamePanel gp) {
        List<crop> list = new ArrayList<>();

        list.add(createCrop(gp, "Parsnip", 50, 35, 1));
        list.add(createCrop(gp, "Cauliflower", 200, 150, 1));
        list.add(createCrop(gp, "Potato", 0, 80, 1));
        list.add(createCrop(gp, "Wheat", 50, 30, 3));
        list.add(createCrop(gp, "Blueberry", 150, 40, 3));
        list.add(createCrop(gp, "Tomato", 90, 60, 1));
        list.add(createCrop(gp, "Hot Pepper", 0, 40, 1));
        list.add(createCrop(gp, "Melon", 0, 250, 1));
        list.add(createCrop(gp, "Cranberry", 0, 25, 10));
        list.add(createCrop(gp, "Pumpkin", 300, 250, 1));
        list.add(createCrop(gp, "Grape", 100, 10, 20));
        return list;
    }

    public static crop getCropByName(GamePanel gp, String cropName) {
        List<crop> allCrops = getAllCrops(gp);
        
        for (crop c : allCrops) {
            if (c.getName().equalsIgnoreCase(cropName)) {
                return c;
            }
        }
        
        return null;
    }
}