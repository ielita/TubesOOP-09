package object;

import java.util.List;
import java.util.Arrays;
import main.GamePanel;

public class NPCData {

    public static List<OBJ_NPC> getAllNPCs(GamePanel gp) {
        return Arrays.asList(
            new OBJ_NPC("Mayor Tadi", "Town Hall",
                List.of("Legend"),
                List.of("Angler", "Crimsonfish", "Glacierfish"),
                List.of(), // Hated = semua selain loved dan liked, handle di OBJ_NPC
                gp),

            new OBJ_NPC("Caroline", "Carpenter Shop",
                List.of("Firewood", "Coal"),
                List.of("Potato", "Wheat"),
                List.of("Hot Pepper"),
                gp),

            new OBJ_NPC("Perry", "Library",
                List.of("Cranberry", "Blueberry"),
                List.of("Wine"),
                List.of("Fish"),
                gp),

            new OBJ_NPC("Dasco", "Casino",
                List.of("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"),
                List.of("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"),
                List.of("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"),
                gp),

            new OBJ_NPC("Emily", "Store",
                List.of("Seeds"),  // semua seeds
                List.of("Catfish", "Salmon", "Sardine"),
                List.of("Coal", "Wood"),
                gp),

            new OBJ_NPC("Abigail", "Abigail's House",
                List.of("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"),
                List.of("Baguette", "Pumpkin Pie", "Wine"),
                List.of("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"),
                gp)
        );
    }
}
