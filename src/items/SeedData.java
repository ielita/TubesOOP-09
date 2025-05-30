package items;

import main.GamePanel;
import java.util.*;

public class SeedData {

    private static seed createSeed(GamePanel gp, String name, String cropType, 
                                  int growthTime, int buyPrice, int sellPrice, String season) {
        return new seed(name, gp, cropType, growthTime, buyPrice, sellPrice, season);
    }

    public static List<seed> getAllSeeds(GamePanel gp) {
        List<seed> list = new ArrayList<>();

        list.add(createSeed(gp, "Parsnip Seeds", "Parsnip", 1, 20, 0, "Spring"));
        list.add(createSeed(gp, "Cauliflower Seeds", "Cauliflower", 5, 80, 0, "Spring"));
        list.add(createSeed(gp, "Potato Seeds", "Potato", 3, 50, 0, "Spring"));
        list.add(createSeed(gp, "Wheat Seeds", "Wheat", 1, 60, 0, "Spring"));

        list.add(createSeed(gp, "Blueberry Seeds", "Blueberry", 7, 80, 0, "Summer"));
        list.add(createSeed(gp, "Tomato Seeds", "Tomato", 3, 50, 0, "Summer"));
        list.add(createSeed(gp, "Hot Pepper Seeds", "Hot Pepper", 1, 40, 0, "Summer"));
        list.add(createSeed(gp, "Melon Seeds", "Melon", 4, 80, 0, "Summer"));

        list.add(createSeed(gp, "Cranberry Seeds", "Cranberry", 2, 100, 0, "Fall"));
        list.add(createSeed(gp, "Pumpkin Seeds", "Pumpkin", 7, 150, 0, "Fall"));
        list.add(createSeed(gp, "Wheat Seeds (Fall)", "Wheat", 1, 60, 0, "Fall"));
        list.add(createSeed(gp, "Grape Seeds", "Grape", 3, 60, 0, "Fall"));

        return list;
    }

    public static List<seed> getSeedsBySeason(GamePanel gp, String season) {
        List<seed> allSeeds = getAllSeeds(gp);
        List<seed> seasonSeeds = new ArrayList<>();
        
        for (seed s : allSeeds) {
            if (s.getSeason().equalsIgnoreCase(season)) {
                seasonSeeds.add(s);
            }
        }

        return seasonSeeds;
    }


    public static seed getSeedByName(GamePanel gp, String seedName) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        for (seed s : allSeeds) {
            if (s.getName().equalsIgnoreCase(seedName)) {
                return s;
            }
        }
        
        return null; 
    }
    
    public static seed getSeedByCropType(GamePanel gp, String cropType) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        for (seed s : allSeeds) {
            if (s.getCropType().equalsIgnoreCase(cropType)) {
                return s;
            }
        }
        
        return null;
    }
    

    public static seed getSeedByIndex(GamePanel gp, int index) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        if (index >= 0 && index < allSeeds.size()) {
            return allSeeds.get(index);
        }
        
        return null; 
    }


    public static List<String> getAvailableSeasons() {
        return Arrays.asList("Spring", "Summer", "Fall");
    }

    public static Map<String, Integer> getSeedCountBySeason(GamePanel gp) {
        Map<String, Integer> seasonCount = new HashMap<>();
        List<seed> allSeeds = getAllSeeds(gp);
        
        for (seed s : allSeeds) {
            String season = s.getSeason();
            seasonCount.put(season, seasonCount.getOrDefault(season, 0) + 1);
        }
        
        return seasonCount;
    }
}