package object;

import java.util.List;
import java.util.Arrays;
import main.GamePanel;

public class NPCData {

    public static List<OBJ_NPC> getAllNPCs(GamePanel gp) {
        return Arrays.asList(
            
            new OBJ_NPC("Emily",
            List.of("Seeds"),  
            List.of("Catfish", "Salmon", "Sardine"),
            List.of("Coal", "Wood"),
            gp),

            new OBJ_NPC("Mayor Tadi",
            List.of("Legend"),
            List.of("Angler", "Crimsonfish", "Glacierfish"),
            List.<String>of(), 
            gp),

            new OBJ_NPC("Caroline",
            List.of("Firewood", "Coal"),
            List.of("Potato", "Wheat"),
            List.of("Hot Pepper"),
            gp),

            new OBJ_NPC("Perry",
            List.of("Cranberry", "Blueberry"),
            List.of("Wine"),
            List.of("Fish"),
            gp),

            new OBJ_NPC("Dasco",
            List.of("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"),
            List.of("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"),
            List.of("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"),
            gp),

            new OBJ_NPC("Abigail",
            List.of("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"),
            List.of("Baguette", "Pumpkin Pie", "Wine"),
            List.of("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"),
            gp),

            new OBJ_NPC("Budiono", List.of("Fish n' Chips", "Sashimi", "Fugu", "Wine", "Pumpkin Pie"),
            List.of("Baguette", "Pumpkin Pie", "Wine"),
            List.of("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"),
            gp),

            new OBJ_NPC ("Celia",
            List.of("Fish n' Chips", "Sashimi", "Fugu", "Wine", "Pumpkin Pie"),
            List.of("Baguette", "Pumpkin Pie", "Wine"),
            List.of("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"),
            gp)
        );
    }
}
