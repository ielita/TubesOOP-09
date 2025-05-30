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
            // Hanya bisa till di farm
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                System.out.println("You can only till soil on your farm!");
                return;
            }
            gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            gp.player.setEnergy(gp.player.getEnergy() - 5);
            
            // Use same calculation method as in Player class
            int playerCenterX = player.worldX + player.solidArea.x + player.solidArea.width / 2;
            int playerCenterY = player.worldY + player.solidArea.y + player.solidArea.height / 2;
            
            int facingX = playerCenterX;
            int facingY = playerCenterY;
            
            switch (player.direction) {
                case "up":    facingY -= gp.tileSize; break;
                case "down":  facingY += gp.tileSize; break;
                case "left":  facingX -= gp.tileSize; break;
                case "right": facingX += gp.tileSize; break;
            }
            
            int col = facingX / gp.tileSize;
            int row = facingY / gp.tileSize;
            
            // Check bounds
            if (col < 0 || row < 0 || col >= gp.tileM.mapManager.maxWorldCol || row >= gp.tileM.mapManager.maxWorldRow) return;
            
            // If tile is grass (id 0), change to tilted (id 7)
            if (gp.tileM.mapManager.mapTileNum[col][row] == 0) {
                gp.tileM.mapManager.mapTileNum[col][row] = 7;
                System.out.println("Tile tilled at col:" + col + " row:" + row);
            }

        }
        
        if (getName().equals("Watering Can")) {
            // Hanya bisa water di farm
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                System.out.println("You can only water crops on your farm!");
                return;
            }
            
            int currentTile = gp.player.getFacingTile();
            int[] coords = player.getFacingTileCoordinates();
            int col = coords[0];
            int row = coords[1];

            if (currentTile == 7) { // dry tilled -> watered tilled
                gp.tileM.mapManager.mapTileNum[col][row] = 9;
                System.out.println("Tilled soil watered at col:" + col + " row:" + row);
                player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 8) { // dry planted -> watered planted
                gp.tileM.mapManager.mapTileNum[col][row] = 10;
                // Mark as watered today
                if (gp.tileM.mapManager.wateredToday == null) {
                    gp.tileM.mapManager.wateredToday = new boolean[gp.tileM.mapManager.maxWorldCol][gp.tileM.mapManager.maxWorldRow];
                }
                gp.tileM.mapManager.wateredToday[col][row] = true;
                System.out.println("Planted crop watered at col:" + col + " row:" + row);
                player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else {
                System.out.println("Nothing to water here!");
            }
        }
        
        if (getName().equals("Sickle")) {
            // Hanya bisa harvest di farm
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                System.out.println("You can only harvest crops on your farm!");
                return;
            }
            int[] coordinates = player.getFacingTileCoordinates();
            int col = coordinates[0];
            int row = coordinates[1];
            
            // Check if tile is ready for harvest
            if (gp.tileM.mapManager.mapTileNum[col][row] == 11) {
                
                // Get the planted seed info to create proper crop
                if (gp.tileM.mapManager.plantedSeeds != null && 
                    gp.tileM.mapManager.plantedSeeds[col][row] != null) {
                    
                    seed plantedSeed = gp.tileM.mapManager.plantedSeeds[col][row];
                    crop harvestedCrop = plantedSeed.plant(); // Use seed's plant() method
                    
                    // Add correct crop to inventory
                    player.addItemToInventory(harvestedCrop, harvestedCrop.getjumlahCropPanen());
                    
                    System.out.println("Harvested " + harvestedCrop.getjumlahCropPanen() + " " + harvestedCrop.getName() + "!");
                    System.out.println("Total value: " + harvestedCrop.getTotalValue() + "g");
                    
                    player.setHasHarvested(true);
                    // Reset tile and clear seed info
                    gp.tileM.mapManager.mapTileNum[col][row] = 7; // Back to tilled
                    gp.tileM.mapManager.plantedSeeds[col][row] = null;
                    if (gp.tileM.mapManager.plantGrowth != null) {
                        gp.tileM.mapManager.plantGrowth[col][row] = 0;
                    }
                } else {
                    // Fallback jika tidak ada seed info
                    crop harvestedCrop = new crop("Unknown Crop", gp, 0, 25, 1);
                    player.addItemToInventory(harvestedCrop, 1);
                    
                    // Reset tile
                    gp.tileM.mapManager.mapTileNum[col][row] = 7;
                    if (gp.tileM.mapManager.plantGrowth != null) {
                        gp.tileM.mapManager.plantGrowth[col][row] = 0;
                    }
                    
                    System.out.println("Harvested unknown crop (seed info missing)");
                }
                
                player.setEnergy(player.getEnergy()-15);
            } else {
                System.out.println("No crops ready to harvest here!");
            }
        }
        
        if (getName().equals("Pickaxe")) {
            // Hanya bisa convert tiles di farm
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
                System.out.println("You can only modify soil on your farm!");
                return;
            }
            int currentTile = gp.player.getFacingTile();
            int[] coords = player.getFacingTileCoordinates();
            int col = coords[0];
            int row = coords[1];
            // Convert tilted tiles back to grass
            if (currentTile == 7) { // tilted -> grass
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                System.out.println("Tilted soil converted to grass at col:" + col + " row:" + row);
                gp.player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 9) { // tilted_w -> grass
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                // Also reset watering status
                if (gp.tileM.mapManager.wateredToday != null) {
                    gp.tileM.mapManager.wateredToday[col][row] = false;
                }
                System.out.println("Watered tilted soil converted to grass at col:" + col + " row:" + row);
                gp.player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 8) { // planted -> grass (destroy crop)
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                gp.tileM.mapManager.plantGrowth[col][row] = 0;
                System.out.println("Planted crop destroyed and converted to grass at col:" + col + " row:" + row);
                gp.player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 10) { // planted_w -> grass (destroy watered crop)
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                gp.tileM.mapManager.plantGrowth[col][row] = 0;
                if (gp.tileM.mapManager.wateredToday != null) {
                    gp.tileM.mapManager.wateredToday[col][row] = false;
                }
                System.out.println("Watered planted crop destroyed and converted to grass at col:" + col + " row:" + row);
                gp.player.setEnergy(player.getEnergy()-5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);   
            }
        }
        
        if (getName().equals("Fishing Rod")) {
            if (player.getFacingTile() == 6) {
                // Kurangi energi
                player.setEnergy(player.getEnergy() - 5);

                // Tambah 15 menit ke waktu
                gp.timeM.setMinute(gp.timeM.getMinute() + 15);

                // Filter ikan yang bisa didapat
                List<fish> allFish = FishData.getAllFish(gp);
                List<fish> availableFish = new ArrayList<>();
                String time = gp.timeM.getTimeString();
                String season = gp.timeM.getSeason();
                String location = gp.tileM.mapManager.getCurrentMap();
                System.out.println("Fishing at " + location + " during " + season + " at " + time);
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

                    // Tentukan range dan tries berdasarkan tipe ikan
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
                    if (!gp.fishingMiniGame.getResultMessage().equals("Anda gagal mendapatkan ikan!") ) { player.setTotalFishCaught(player.getTotalFishCaught() + 1); player.fishCaught.addItem(caught, 1); }
                    gp.gameState = gp.fishingMiniGameState;
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