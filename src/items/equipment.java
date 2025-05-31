package items;

import entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.GamePanel;
import main.UtilityTool;

public class equipment extends Item {
    public equipment(String name, GamePanel gp) {
        super(name, gp);
    }

    public void use(Player player) {
        GamePanel gp = player.getGp();

        if (getName().equals("Hoe")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) return;

            gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            player.setEnergy(player.getEnergy() - 5);

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

            if (col < 0 || row < 0 || col >= gp.tileM.mapManager.maxWorldCol || row >= gp.tileM.mapManager.maxWorldRow) return;

            if (gp.tileM.mapManager.mapTileNum[col][row] == 0) {
                gp.tileM.mapManager.mapTileNum[col][row] = 7;
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            }
        }

        if (getName().equals("Watering Can")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) return;

            int currentTile = player.getFacingTile();
            int[] coords = player.getFacingTileCoordinates();
            int col = coords[0];
            int row = coords[1];

            if (currentTile == 7) {
                gp.tileM.mapManager.mapTileNum[col][row] = 9;
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 8) {
                gp.tileM.mapManager.mapTileNum[col][row] = 10;
                if (gp.tileM.mapManager.wateredToday == null) {
                    gp.tileM.mapManager.wateredToday = new boolean[gp.tileM.mapManager.maxWorldCol][gp.tileM.mapManager.maxWorldRow];
                }
                gp.tileM.mapManager.wateredToday[col][row] = true;
            }

            player.setEnergy(player.getEnergy() - 5);
            gp.timeM.setMinute(gp.timeM.getMinute() + 5);
        }

        if (getName().equals("Sickle")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) return;

            int[] coordinates = player.getFacingTileCoordinates();
            int col = coordinates[0];
            int row = coordinates[1];

            if (gp.tileM.mapManager.mapTileNum[col][row] == 11) {
                if (gp.tileM.mapManager.plantedSeeds != null && gp.tileM.mapManager.plantedSeeds[col][row] != null) {
                    seed plantedSeed = gp.tileM.mapManager.plantedSeeds[col][row];
                    crop harvestedCrop = plantedSeed.plant();

                    player.addItemToInventory(harvestedCrop, harvestedCrop.getjumlahCropPanen());
                    player.setTotalHarvest(player.getTotalHarvest() + harvestedCrop.getjumlahCropPanen());

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
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            }
        }

        if (getName().equals("Pickaxe")) {
            if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) return;

            int currentTile = player.getFacingTile();
            int[] coords = player.getFacingTileCoordinates();
            int col = coords[0];
            int row = coords[1];

            if (currentTile == 7 || currentTile == 9) {
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                if (currentTile == 9 && gp.tileM.mapManager.wateredToday != null) {
                    gp.tileM.mapManager.wateredToday[col][row] = false;
                }
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            } else if (currentTile == 8 || currentTile == 10) {
                gp.tileM.mapManager.mapTileNum[col][row] = 0;
                gp.tileM.mapManager.plantGrowth[col][row] = 0;
                if (currentTile == 10 && gp.tileM.mapManager.wateredToday != null) {
                    gp.tileM.mapManager.wateredToday[col][row] = false;
                }
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 5);
            }
        }

        if (getName().equals("Fishing Rod")) {
            if (player.getFacingTile() == 6) {
                player.setEnergy(player.getEnergy() - 5);
                gp.timeM.setMinute(gp.timeM.getMinute() + 15);

                List<fish> allFish = FishData.getAllFish(gp);
                List<fish> availableFish = new ArrayList<>();
                String time = gp.timeM.getTimeString();
                String season = gp.timeM.getSeason();
                String location = gp.tileM.mapManager.getCurrentMap();
                String weather;
                if (gp.timeM.isRainyDay()) weather = "Rainy";
                else weather = "Sunny";

                for (fish f : allFish) {
                    boolean seasonMatch = f.getSeasons().contains("Any") || f.getSeasons().contains(season);
                    boolean locationMatch = f.getLocation().contains("Any") || f.getLocation().contains(location);
                    boolean timeMatch = false;
                    boolean weatherMatch = f.getWeather().contains("Any") || f.getWeather().contains(weather);

                    UtilityTool util = new UtilityTool();
                    for (String t : f.getTime()) {
                        if (t.equals("Any") || util.timeInRange(time, t)) {
                            timeMatch = true;
                            break;
                        }
                    }

                    if (seasonMatch && locationMatch && timeMatch && weatherMatch) {
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
                    if (!gp.fishingMiniGame.getResultMessage().equals("Anda gagal mendapatkan ikan!")) {
                        player.setTotalFishCaught(player.getTotalFishCaught() + 1);
                        player.fishCaught.addItem(caught, 1);
                    }
                    gp.gameState = gp.fishingMiniGameState;
                }
            }
        }
    }

    public void getinfo() {
        System.out.println();
    }
}
