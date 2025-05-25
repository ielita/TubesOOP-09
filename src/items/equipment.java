package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Player;
import main.GamePanel;
import main.UtilityTool;

public class equipment extends Item{
    public equipment(String name, GamePanel gp) {
        super(name, gp);
    }


    public void use(Player player) {
        
        String time = player.getGp().timeM.getTimeString();
        String season = player.getGp().timeM.getSeason();
        String location = player.getGp().mapM.getCurrentMap();
        // String weather = player.getGp().timeM.getWeather(); // Weatehr Belum diimplementasikan

        if (player.isFacingWater()) {
            if (getName().equals("Fishing Rod")) {
                List<fish> allFish = player.listFish;
                List<fish> availableFish = new ArrayList<>();

                for (fish f : allFish) {
                    boolean seasonMatch = f.getSeasons().contains("Any") || f.getSeasons().contains(season);
                    boolean locationMatch = f.getLocation().contains("Any") || f.getLocation().contains(location);
                   
                    boolean timeMatch = false;
                    UtilityTool util = new UtilityTool();
                    for (String t : f.getTime()) {
                        if (t.equals("Any") || util.timeInRange(time, t)) {
                            timeMatch = true;
                            break;
                        }
                    }
                    // Belum check weather

                    if (seasonMatch && locationMatch && timeMatch) {
                        availableFish.add(f);
                    }
                }

                if (!availableFish.isEmpty()) {
                    fish caught = availableFish.get(new Random().nextInt(availableFish.size()));
                    player.addItemToInventory(caught, 1);
                    System.out.println("Kamu mendapatkan ikan: " + caught.getName());
                } else {
                    System.out.println("Tidak ada ikan yang cocok di lokasi, season, dan waktu ini!");
                }
            }
        }
    }

    public void getinfo(){
        System.out.println("This is an equipment item.");
    }
}