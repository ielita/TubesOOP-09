package items;

import main.GamePanel;
import java.util.*;

public class CropData {
    public static List<crop> getAllCrops(GamePanel gp) {
        List<crop> list = new ArrayList<>();

        // 1. Parsnip - Harga Beli: 50g, Harga Jual: 35g, Jumlah: 1
        list.add(new crop("Parsnip", gp, 50, 35, 1));

        // 2. Cauliflower - Harga Beli: 200g, Harga Jual: 150g, Jumlah: 1
        list.add(new crop("Cauliflower", gp, 200, 150, 1));

        // 3. Potato - Harga Beli: -, Harga Jual: 80g, Jumlah: 1
        list.add(new crop("Potato", gp, 0, 80, 1));

        // 4. Wheat - Harga Beli: 50g, Harga Jual: 30g, Jumlah: 3
        list.add(new crop("Wheat", gp, 50, 30, 3));

        // 5. Blueberry - Harga Beli: 150g, Harga Jual: 40g, Jumlah: 3
        list.add(new crop("Blueberry", gp, 150, 40, 3));

        // 6. Tomato - Harga Beli: 90g, Harga Jual: 60g, Jumlah: 1
        list.add(new crop("Tomato", gp, 90, 60, 1));

        // 7. Hot Pepper - Harga Beli: -, Harga Jual: 40g, Jumlah: 1
        list.add(new crop("Hot Pepper", gp, 0, 40, 1));

        // 8. Melon - Harga Beli: -, Harga Jual: 250g, Jumlah: 1
        list.add(new crop("Melon", gp, 0, 250, 1));

        // 9. Cranberry - Harga Beli: -, Harga Jual: 25g, Jumlah: 10
        list.add(new crop("Cranberry", gp, 0, 25, 10));

        // 10. Pumpkin - Harga Beli: 300g, Harga Jual: 250g, Jumlah: 1
        list.add(new crop("Pumpkin", gp, 300, 250, 1));

        // 11. Grape - Harga Beli: 100g, Harga Jual: 10g, Jumlah: 20
        list.add(new crop("Grape", gp, 100, 10, 20));

        return list;
    }

    // Get crop by name
    public static crop getCropByName(GamePanel gp, String cropName) {
        List<crop> allCrops = getAllCrops(gp);
        
        for (crop c : allCrops) {
            if (c.getName().equalsIgnoreCase(cropName)) {
                return c;
            }
        }
        
        return null;
    }

    // Get high-value crops (sell price > 100g each)
    public static List<crop> getHighValueCrops(GamePanel gp) {
        List<crop> allCrops = getAllCrops(gp);
        List<crop> highValueCrops = new ArrayList<>();
        
        for (crop c : allCrops) {
            if (c.gethargajual() > 100) {
                highValueCrops.add(c);
            }
        }
        
        return highValueCrops;
    }

    // Get multi-harvest crops (yield > 1)
    public static List<crop> getMultiHarvestCrops(GamePanel gp) {
        List<crop> allCrops = getAllCrops(gp);
        List<crop> multiHarvestCrops = new ArrayList<>();
        
        for (crop c : allCrops) {
            if (c.getjumlahCropPanen() > 1) {
                multiHarvestCrops.add(c);
            }
        }
        
        return multiHarvestCrops;
    }
}