package entity;

import java.util.ArrayList;



import java.util.List;

import entity.NPC;
import items.*;
import main.GamePanel;

public class NPCdata {
    public static List<NPC> getAllNPC(GamePanel gp) {
        List<NPC> list = new ArrayList<>();

        // 🟢 Common Fish
        // Make sure the constructor arguments match the NPC.java constructor definition
        list.add(new NPC(
            gp,
            "Mayor Tadi",
            0,
            List.of("Legend"),
            List.of("Angler, Crimsonfish, Glacierfish"),
            List.of("Any"), // all but liked and loved
            "Single"
        ));
        list.add(new NPC(
            gp,
            "Caroline",
            0,
            List.of("Firewood", "Coal"),
            List.of("Potato","Wheat"),
            List.of("Hot Pepper"),
            "Single"
        ));
        list.add(new NPC(
            gp,
            "Perry",
            0,
            List.of("Cranberry", "Blueberry"),
            List.of("Angler", "Crimsonfish", "Glacierfish"),
            List.of("Any"), // all fish
            "Single"
        ));
        list.add(new NPC(
            gp,
            "Dasco",
            0,
            List.of("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"),
            List.of("Fish Sandwich", "Fish Stew", "Baguette", "Fish n' Chips"),
            List.of("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"),
            "Single"
        ));
        list.add(new NPC(
            gp,
            "Emily",
            0,
            List.of("Any"), // all seeds
            List.of("Catfish", "Salmon", "Sardine"),
            List.of("Coal", "Wood"),
            "Single"
        ));
        list.add(new NPC(
            gp,
            "Abigail",
            0,
            List.of("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"),
            List.of("Baguette", "Pumpkin Pie", "Wine"),
            List.of("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"),
            "Single"
        ));
        return list;
    }
}
