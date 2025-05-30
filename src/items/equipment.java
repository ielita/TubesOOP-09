package items;

import entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.GamePanel;
import main.UtilityTool;

public class equipment extends Item{
    public equipment(String name, GamePanel gp) {
        super(name, gp);
    }

    public void use(Player player) {
        GamePanel gp = player.getGp();
        
        if (getName().equals("Hoe")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                return;
            }
            
            int currentTile = player.getFacingTile();
            
            if (currentTile == 0) {
                int[] coordinates = player.getFacingTileCoordinates();
                int col = coordinates[0];
                int row = coordinates[1];
                
                if (col >= 0 && row >= 0 && col < gp.tileM.mapManager.maxWorldCol && row < gp.tileM.mapManager.maxWorldRow) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 7;
                    
                    gp.timeM.setMinute(gp.timeM.getMinute() + 5);
                    player.setEnergy(player.getEnergy() - 5);
                }
            }
        }
        
        if (getName().equals("Watering Can")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                return;
            }
            
            int currentTile = player.getFacingTile();
            
            if (currentTile == 7 || currentTile == 8) {
                int[] coordinates = player.getFacingTileCoordinates();
                int col = coordinates[0];
                int row = coordinates[1];
                
                if (col >= 0 && row >= 0 && col < gp.tileM.mapManager.maxWorldCol && row < gp.tileM.mapManager.maxWorldRow) {
                    if (currentTile == 7) {
                        gp.tileM.mapManager.mapTileNum[col][row] = 9;
                    } else if (currentTile == 8) {
                        gp.tileM.mapManager.mapTileNum[col][row] = 10;
                        
                        if (gp.tileM.mapManager.wateredToday != null) {
                        gp.tileM.mapManager.wateredToday[col][row] = true;
                        }
                    
                    player.setEnergy(player.getEnergy() - 5);
                    gp.timeM.setMinute(gp.timeM.getMinute() + 5);
                }
            }
        }
        }
        
        if (getName().equals("Sickle")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                return;
            }
            
            int currentTile = player.getFacingTile();
            
            if (currentTile == 11) {
                int[] coordinates = player.getFacingTileCoordinates();
                int col = coordinates[0];
                int row = coordinates[1];
                
                if (col >= 0 && row >= 0 && col < gp.tileM.mapManager.maxWorldCol && row < gp.tileM.mapManager.maxWorldRow) {
                    if (gp.tileM.mapManager.plantedSeeds != null && 
                        gp.tileM.mapManager.plantedSeeds[col][row] != null) {
                        
                        seed plantedSeed = gp.tileM.mapManager.plantedSeeds[col][row];
                        crop harvestedCrop = plantedSeed.plant();
                        
                        player.addItemToInventory(harvestedCrop, harvestedCrop.getjumlahCropPanen());
                        
                        gp.tileM.mapManager.mapTileNum[col][row] = 7;
                        gp.tileM.mapManager.plantedSeeds[col][row] = null;
                        if (gp.tileM.mapManager.plantGrowth != null) {
                            gp.tileM.mapManager.plantGrowth[col][row] = 0;
                        }
                        
                    } else {
                        crop harvestedCrop = new crop("Unknown Crop", gp, 0, 25, 1);
                        player.addItemToInventory(harvestedCrop, 1);
                        
                        gp.tileM.mapManager.mapTileNum[col][row] = 7;
                        if (gp.tileM.mapManager.plantGrowth != null) {
                            gp.tileM.mapManager.plantGrowth[col][row] = 0;
                        }
                        
                    }
                    
                    gp.timeM.setMinute(gp.timeM.getMinute() + 5);
                    player.setEnergy(player.getEnergy() - 15);
                }
            }
        }
        
        if (getName().equals("Pickaxe")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                return;
            }
            
            int currentTile = player.getFacingTile();
            
            if (currentTile == 7 || currentTile == 9 || currentTile == 8 || currentTile == 10) {
                int[] coordinates = player.getFacingTileCoordinates();
                int col = coordinates[0];
                int row = coordinates[1];
                
                if (col >= 0 && row >= 0 && col < gp.tileM.mapManager.maxWorldCol && row < gp.tileM.mapManager.maxWorldRow) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 0;
                    
                    if (currentTile == 8 || currentTile == 10) {
                        if (gp.tileM.mapManager.plantedSeeds != null) {
                            gp.tileM.mapManager.plantedSeeds[col][row] = null;
                        }
                        if (gp.tileM.mapManager.plantGrowth != null) {
                            gp.tileM.mapManager.plantGrowth[col][row] = 0;
                        }
                    }
                    
                    if (currentTile == 9 || currentTile == 10) {
                        if (gp.tileM.mapManager.wateredToday != null) {
                            gp.tileM.mapManager.wateredToday[col][row] = false;
                        }
                    }
                    
                    player.setEnergy(player.getEnergy() - 5);
                    gp.timeM.setMinute(gp.timeM.getMinute() + 5);
                    
                }
            }
        }
        
        if (getName().equals("Fishing Rod")) {
            int facingTile = player.getFacingTile();
            
            if (facingTile == 6) {
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 15);
                
                List<fish> allFish = FishData.getAllFish(gp);
                List<fish> availableFish = new ArrayList<>();
                String time = gp.timeM.getTimeString();
                String season = gp.timeM.getSeason();
                String location = gp.tileM.mapManager.getCurrentMap();
                
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
                    
                    if (seasonMatch && locationMatch && timeMatch) {
                        availableFish.add(f);
                    }
                }

                if (!availableFish.isEmpty()) {
                    fish caught = availableFish.get(new Random().nextInt(availableFish.size()));
                    int min = 1, max = 10, tries = 10;
                    String type = caught.getRarity();
                    
                    if ("common".equalsIgnoreCase(type)) {
                        min = 1; max = 10; tries = 10;
                    } else if ("regular".equalsIgnoreCase(type)) {
                        min = 1; max = 100; tries = 10;
                    } else if ("legendary".equalsIgnoreCase(type)) {
                        min = 1; max = 500; tries = 7;
                    }
                    
                    gp.fishingMiniGame.start(caught, min, max, tries);
                    gp.gameState = gp.fishingMiniGameState;
                    
                }
            }
        }
    }

    public void getinfo(){
    }
}
