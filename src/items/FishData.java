package items;

import main.GamePanel;
import java.util.*;

public class FishData {
    public static List<fish> getAllFish(GamePanel gp) {
        List<fish> list = new ArrayList<>();

        // 🟢 Common Fish
        list.add(new fish(
            "Bullhead",
            List.of("Mountain Lake"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));
        list.add(new fish(
            "Carp",
            List.of("Mountain Lake", "Pond"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));
        list.add(new fish(
            "Chub",
            List.of("Forest River", "Mountain Lake"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));

        // 🔵 Regular Fish
        list.add(new fish(
            "Largemouth Bass",
            List.of("Mountain Lake"),
            List.of("Any"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Rainbow Trout",
            List.of("Forest River", "Mountain Lake"),
            List.of("Summer"),
            List.of("06.00–18.00"),
            List.of("Sunny"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Sturgeon",
            List.of("Mountain Lake"),
            List.of("Summer", "Winter"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Midnight Carp",
            List.of("Mountain Lake", "Pond"),
            List.of("Winter", "Fall"),
            List.of("20.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Flounder",
            List.of("Ocean"),
            List.of("Spring", "Summer"),
            List.of("06.00–22.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Halibut",
            List.of("Ocean"),
            List.of("Any"),
            List.of("06.00–11.00", "19.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Octopus",
            List.of("Ocean"),
            List.of("Summer"),
            List.of("06.00–22.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Pufferfish",
            List.of("Ocean"),
            List.of("Summer"),
            List.of("00.00–16.00"),
            List.of("Sunny"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Sardine",
            List.of("Any"),
            List.of("Any"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Super Cucumber",
            List.of("Ocean"),
            List.of("Summer", "Fall", "Winter"),
            List.of("18.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Catfish",
            List.of("farm", "Pond"),
            List.of("Spring", "Summer", "Fall"),
            List.of("06.00–22.00"),
            List.of("Rainy"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Salmon",
            List.of("Forest River"),
            List.of("Fall"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));

        // 🟡 Legendary Fish
        list.add(new fish(
            "Angler",
            List.of("Pond"),
            List.of("Fall"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Crimsonfish",
            List.of("Ocean"),
            List.of("Summer"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Glacierfish",
            List.of("Forest River"),
            List.of("Winter"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Legend",
            List.of("Mountain Lake"),
            List.of("Spring"),
            List.of("08.00–20.00"),
            List.of("Rainy"),
            "Legendary",
            gp
        ));

        return list;
    }
}