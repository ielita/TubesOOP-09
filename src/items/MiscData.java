package items;

import main.GamePanel;
import java.util.*;

public class MiscData {
    
    private static misc createMisc(GamePanel gp, String name, String category, int hargabeli, int hargajual) {
        return new misc(name, gp, category, hargabeli, hargajual);
    }
    
    public static List<misc> getAllMiscItems(GamePanel gp) {
        List<misc> list = new ArrayList<>();

        list.add(createMisc(gp, "Coal", "Fuel", 50, 15));
        list.add(createMisc(gp, "Firewood", "Fuel", 10, 5));
        list.add(createMisc(gp, "Recipe_1", "Recipe", 100, 0));
        list.add(createMisc(gp, "Recipe_10", "Recipe", 150, 0));

        return list;
    }

    public static misc getMiscByName(GamePanel gp, String miscName) {
        List<misc> allMisc = getAllMiscItems(gp);
        
        for (misc m : allMisc) {
            if (m.getName().equalsIgnoreCase(miscName)) {
                return m;
            }
        }
        
        return null;
    }

    public static List<misc> getMiscByCategory(GamePanel gp, String category) {
        List<misc> allMisc = getAllMiscItems(gp);
        List<misc> categoryMisc = new ArrayList<>();
        
        for (misc m : allMisc) {
            if (m.getCategory().equalsIgnoreCase(category)) {
                categoryMisc.add(m);
            }
        }
        
        return categoryMisc;
    }
}