package items;

import main.GamePanel;
import java.util.*;

public class SeedData {
    public static List<seed> getAllSeeds(GamePanel gp) {
        List<seed> list = new ArrayList<>();

        // 1. Parsnip Seeds - 4 days growth
        seed parsnipSeeds = new seed(
            "Parsnip Seeds",
            gp,
            "A common root vegetable. Takes 1 days to mature.",
            "Parsnip",
            1
        );
        parsnipSeeds.setHargabeli(20);
        parsnipSeeds.setHargajual(10);
        list.add(parsnipSeeds);

        // 2. Cauliflower Seeds - 12 days growth
        seed cauliflowerSeeds = new seed(
            "Cauliflower Seeds",
            gp,
            "A valuable vegetable. Takes 5 days to mature.",
            "Cauliflower",
            5
        );
        cauliflowerSeeds.setHargabeli(80);
        cauliflowerSeeds.setHargajual(40);
        list.add(cauliflowerSeeds);

        // 3. Potato Seeds - 6 days growth
        seed potatoSeeds = new seed(
            "Potato Seeds",
            gp,
            "A versatile tuber. Takes 3 days to mature.",
            "Potato",
            3
        );
        potatoSeeds.setHargabeli(50);
        potatoSeeds.setHargajual(25);
        list.add(potatoSeeds);

        // 4. Wheat Seeds - 4 days growth, yields 3
        seed wheatSeeds = new seed(
            "Wheat Seeds",
            gp,
            "A grain crop. Takes 1 days to mature. Yields 3 crops per harvest.",
            "Wheat",
            1
        );
        wheatSeeds.setHargabeli(50);
        wheatSeeds.setHargajual(25);
        list.add(wheatSeeds);

        // 5. Blueberry Seeds - 13 days growth, yields 3
        seed blueberrySeeds = new seed(
            "Blueberry Seeds",
            gp,
            "A fruit bush. Takes 7 days to mature. Yields 3 berries per harvest.",
            "Blueberry",
            7
        );
        blueberrySeeds.setHargabeli(80);
        blueberrySeeds.setHargajual(40);
        list.add(blueberrySeeds);

        // 6. Tomato Seeds - 11 days growth
        seed tomatoSeeds = new seed(
            "Tomato Seeds",
            gp,
            "A popular vegetable. Takes 3 days to mature.",
            "Tomato",
            3
        );
        tomatoSeeds.setHargabeli(50);
        tomatoSeeds.setHargajual(25);
        list.add(tomatoSeeds);

        // 7. Hot Pepper Seeds - 5 days growth
        seed hotPepperSeeds = new seed(
            "Hot Pepper Seeds",
            gp,
            "A spicy pepper. Takes 1 days to mature.",
            "Hot Pepper",
            1
        );
        hotPepperSeeds.setHargabeli(40);
        hotPepperSeeds.setHargajual(20);
        list.add(hotPepperSeeds);

        // 8. Melon Seeds - 12 days growth
        seed melonSeeds = new seed(
            "Melon Seeds",
            gp,
            "A large, sweet fruit. Takes 4 days to mature.",
            "Melon",
            4
        );
        melonSeeds.setHargabeli(80);
        melonSeeds.setHargajual(40);
        list.add(melonSeeds);

        // 9. Cranberry Seeds - 7 days growth, yields 10
        seed cranberrySeeds = new seed(
            "Cranberry Seeds",
            gp,
            "A tart berry bush. Takes 2 days to mature. Yields 10 cranberries per harvest.",
            "Cranberry",
            2
        );
        cranberrySeeds.setHargabeli(100);
        cranberrySeeds.setHargajual(50);
        list.add(cranberrySeeds);

        // 10. Pumpkin Seeds - 13 days growth
        seed pumpkinSeeds = new seed(
            "Pumpkin Seeds",
            gp,
            "A large orange gourd. Takes 7 days to mature.",
            "Pumpkin",
            7
        );
        pumpkinSeeds.setHargabeli(150);
        pumpkinSeeds.setHargajual(75);
        list.add(pumpkinSeeds);

        // 11. Grape Seeds - 10 days growth, yields 20
        seed grapeSeeds = new seed(
            "Grape Seeds",
            gp,
            "A vine fruit. Takes 3 days to mature. Yields 20 grapes per harvest.",
            "Grape",
            3
        );
        grapeSeeds.setHargabeli(60);
        grapeSeeds.setHargajual(30);
        list.add(grapeSeeds);

        return list;
    }

    // Get individual seed by name
    public static seed getSeedByName(GamePanel gp, String seedName) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        for (seed s : allSeeds) {
            if (s.getName().equalsIgnoreCase(seedName)) {
                return s;
            }
        }
        
        return null; // Seed not found
    }
    
    // Get individual seed by crop type
    public static seed getSeedByCropType(GamePanel gp, String cropType) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        for (seed s : allSeeds) {
            if (s.getCropType().equalsIgnoreCase(cropType)) {
                return s;
            }
        }
        
        return null; // Seed not found
    }
    
    // Get seed by index (0-10 based on your data)
    public static seed getSeedByIndex(GamePanel gp, int index) {
        List<seed> allSeeds = getAllSeeds(gp);
        
        if (index >= 0 && index < allSeeds.size()) {
            return allSeeds.get(index);
        }
        
        return null; // Invalid index
    }

    // Get seeds by crop yield (high yield vs single crop)
    public static List<seed> getHighYieldSeeds(GamePanel gp) {
        List<seed> allSeeds = getAllSeeds(gp);
        List<seed> highYieldSeeds = new ArrayList<>();
        
        for (seed s : allSeeds) {
            if (s.getCropYield() > 1) {
                highYieldSeeds.add(s);
            }
        }
        
        return highYieldSeeds;
    }

    // Get seeds by profitability
    public static List<seed> getProfitableSeeds(GamePanel gp) {
        List<seed> allSeeds = getAllSeeds(gp);
        List<seed> profitableSeeds = new ArrayList<>();
        
        for (seed s : allSeeds) {
            if (s.getEstimatedProfit() > 0) {
                profitableSeeds.add(s);
            }
        }
        
        // Sort by profitability (highest first)
        profitableSeeds.sort((s1, s2) -> 
            Integer.compare(s2.getEstimatedProfit(), s1.getEstimatedProfit()));
        
        return profitableSeeds;
    }

    // Get seeds by growth time range
    public static List<seed> getSeedsByGrowthTime(GamePanel gp, int minDays, int maxDays) {
        List<seed> allSeeds = getAllSeeds(gp);
        List<seed> filteredSeeds = new ArrayList<>();
        
        for (seed s : allSeeds) {
            if (s.getGrowthTime() >= minDays && s.getGrowthTime() <= maxDays) {
                filteredSeeds.add(s);
            }
        }
        
        return filteredSeeds;
    }
}