package items;

import main.GamePanel;
import java.util.*;

public class FishData {
    public static List<fish> getAllFish(GamePanel gp) {
        List<fish> list = new ArrayList<>();

        // 🟢 Common Fish
        list.add(new fish(
            "Bullhead",
            List.of("mountainlake"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));
        list.add(new fish(
            "Carp",
            List.of("mountainlake", "farm"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));
        list.add(new fish(
            "Chub",
            List.of("forestriver", "mountainlake"),
            List.of("Any"),
            List.of("Any"),
            List.of("Any"),
            "Common",
            gp
        ));

        // 🔵 Regular Fish
        list.add(new fish(
            "Largemouth Bass",
            List.of("mountainlake"),
            List.of("Any"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Rainbow Trout",
            List.of("forestriver", "mountainlake"),
            List.of("Summer"),
            List.of("06.00–18.00"),
            List.of("Sunny"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Sturgeon",
            List.of("mountainlake"),
            List.of("Summer", "Winter"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Midnight Carp",
            List.of("mountainlake", "farm"),
            List.of("Winter", "Fall"),
            List.of("20.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Flounder",
            List.of("ocean"),
            List.of("Spring", "Summer"),
            List.of("06.00–22.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Halibut",
            List.of("ocean"),
            List.of("Any"),
            List.of("06.00–11.00", "19.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Octopus",
            List.of("ocean"),
            List.of("Summer"),
            List.of("06.00–22.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Pufferfish",
            List.of("ocean"),
            List.of("Summer"),
            List.of("00.00–16.00"),
            List.of("Sunny"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Sardine",
            List.of("ocean"),
            List.of("Any"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Super Cucumber",
            List.of("ocean"),
            List.of("Summer", "Fall", "Winter"),
            List.of("18.00–02.00"),
            List.of("Any"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Catfish",
            List.of("forestriver", "farm"),
            List.of("Spring", "Summer", "Fall"),
            List.of("06.00–22.00"),
            List.of("Rainy"),
            "Regular",
            gp
        ));
        list.add(new fish(
            "Salmon",
            List.of("forestriver"),
            List.of("Fall"),
            List.of("06.00–18.00"),
            List.of("Any"),
            "Regular",
            gp
        ));

        // 🟡 Legendary Fish
        list.add(new fish(
            "Angler",
            List.of("farm"),
            List.of("Fall"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Crimsonfish",
            List.of("ocean"),
            List.of("Summer"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Glacierfish",
            List.of("forestriver"),
            List.of("Winter"),
            List.of("08.00–20.00"),
            List.of("Any"),
            "Legendary",
            gp
        ));
        list.add(new fish(
            "Legend",
            List.of("mountainlake"),
            List.of("Spring"),
            List.of("08.00–20.00"),
            List.of("Rainy"),
            "Legendary",
            gp
        ));

        return list;
    }
}