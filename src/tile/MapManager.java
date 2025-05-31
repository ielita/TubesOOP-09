package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import main.GamePanel;
import items.seed;

public class MapManager {
    GamePanel gp;
    public String currentMap;
    public String selectedFarmMap; // Store the randomly selected farm map
    public int[][] mapTileNum;
    public int maxWorldCol;
    public int maxWorldRow;
    private float brightness = 1.0f;

    public int[][] plantGrowth;
    public boolean[][] wateredToday;
    public seed[][] plantedSeeds;

    private int[][] savedFarmTiles;
    private int[][] savedFarmGrowth;
    private boolean[][] savedFarmWatered;
    private seed[][] savedFarmSeeds;
    private int savedFarmMaxCol;
    private int savedFarmMaxRow;

    public MapManager(GamePanel gp) {
        this.gp = gp;
        currentMap = "insideHouse";
        selectRandomFarmMap();
        loadMapConfig(currentMap);
    }

    private void selectRandomFarmMap() {
        String[] farmMaps = {"farm","farm1","farm2","farm3"};
        Random random = new Random();
        selectedFarmMap = farmMaps[random.nextInt(farmMaps.length)];
        System.out.println("Selected farm map: " + selectedFarmMap);
    }

    public void loadMapConfig(String mapName) {
        String actualMapName = mapName.equals("farm") ? selectedFarmMap : mapName;
        try {
            FileInputStream fis = new FileInputStream("res/maps/" + actualMapName + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String[] dimensions = br.readLine().split(" ");
            maxWorldCol = Integer.parseInt(dimensions[0]);
            maxWorldRow = Integer.parseInt(dimensions[1]);
            initializeArrays();
            loadMap(br);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeArrays() {
        mapTileNum = new int[maxWorldCol][maxWorldRow];
        if (isFarmMap(currentMap)) {
            if (plantGrowth == null) {
                plantGrowth = new int[maxWorldCol][maxWorldRow];
            }
            
            if (wateredToday == null) {
                wateredToday = new boolean[maxWorldCol][maxWorldRow];
            }
            
            if (plantedSeeds == null) {
                plantedSeeds = new seed[maxWorldCol][maxWorldRow];
            }
        }
    }

    private boolean isFarmMap(String mapName) {
        return mapName.equals("farm") || mapName.startsWith("farm");
    }

    public void changeMap(String newMap, int playerX, int playerY) {
        // Save current farm state if leaving a farm
        if (isFarmMap(currentMap)) {
            saveFarmState();
        }
        
        currentMap = newMap;
        loadMapConfig(newMap);
        
        if (isFarmMap(newMap)) {
            restoreFarmState();
        }
        
        gp.aSetter.setObject(newMap);
        gp.player.setPosition(playerX, playerY);
    }

    private void saveFarmState() {
        if (mapTileNum == null) return;
        
        savedFarmMaxCol = maxWorldCol;
        savedFarmMaxRow = maxWorldRow;
        savedFarmTiles = new int[maxWorldCol][maxWorldRow];
        
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                savedFarmTiles[col][row] = mapTileNum[col][row];
            }
        }
        
        if (plantGrowth != null) {
            savedFarmGrowth = new int[maxWorldCol][maxWorldRow];
            savedFarmWatered = new boolean[maxWorldCol][maxWorldRow];
            savedFarmSeeds = new seed[maxWorldCol][maxWorldRow];
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    savedFarmGrowth[col][row] = plantGrowth[col][row];
                    savedFarmWatered[col][row] = wateredToday[col][row];
                    savedFarmSeeds[col][row] = plantedSeeds[col][row];
                }
            }
        }
    }

    private void restoreFarmState() {
        if (savedFarmTiles == null) return;
        
        // Check if dimensions match
        if (savedFarmMaxCol != maxWorldCol || savedFarmMaxRow != maxWorldRow) {
            return;
        }
        
        for (int col = 0; col < Math.min(maxWorldCol, savedFarmMaxCol); col++) {
            for (int row = 0; row < Math.min(maxWorldRow, savedFarmMaxRow); row++) {
                mapTileNum[col][row] = savedFarmTiles[col][row];
            }
        }
        
        if (savedFarmGrowth != null && plantGrowth != null) {
            for (int col = 0; col < Math.min(maxWorldCol, savedFarmMaxCol); col++) {
                for (int row = 0; row < Math.min(maxWorldRow, savedFarmMaxRow); row++) {
                    plantGrowth[col][row] = savedFarmGrowth[col][row];
                    wateredToday[col][row] = savedFarmWatered[col][row];
                    plantedSeeds[col][row] = savedFarmSeeds[col][row];
                }
            }
        }
    }

    public String getSelectedFarmMap() {
        return selectedFarmMap;
    }

    private void loadMap(BufferedReader br) {
        try {
            int col = 0, row = 0;
            while (row < maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.split(" ");
                
                while (col < maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rainyDay() {
        if (gp.timeM.isRainyDay()) {
            if (isFarmMap(currentMap) && wateredToday != null) {
                for (int col = 0; col < maxWorldCol; col++) {
                    for (int row = 0; row < maxWorldRow; row++) {
                        wateredToday[col][row] = true;
                    }
                }
            }
            if (!isFarmMap(currentMap) && savedFarmWatered != null) {
                for (int col = 0; col < savedFarmMaxCol; col++) {
                    for (int row = 0; row < savedFarmMaxRow; row++) {
                        savedFarmWatered[col][row] = true;
                    }
                }
            }
        } 
    }

    public void updatePlantGrowth() {
        if (!isFarmMap(currentMap) || plantGrowth == null) {
            if (savedFarmGrowth != null) {
                updateSavedFarmState();
            }
            return;
        }
        
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                if (plantedSeeds[col][row] != null) {
                    if (wateredToday[col][row]) {
                        plantGrowth[col][row]++;
                        if (plantGrowth[col][row] >= plantedSeeds[col][row].getGrowthTime()) {
                            mapTileNum[col][row] = 11;
                        }
                    }
                }
                wateredToday[col][row] = false;
            }
        }
    }

    private void updateSavedFarmState() {
        if (savedFarmTiles == null) return;
        for (int col = 0; col < savedFarmMaxCol; col++) {
            for (int row = 0; row < savedFarmMaxRow; row++) {
                if (savedFarmSeeds[col][row] != null) {
                    if (savedFarmWatered[col][row]) {
                        savedFarmGrowth[col][row]++;
                        if (savedFarmGrowth[col][row] >= savedFarmSeeds[col][row].getGrowthTime()) {
                            savedFarmTiles[col][row] = 11;
                        }
                    }
                }
                savedFarmWatered[col][row] = false;
            }
        }
    }

    public void harvestCrop(int col, int row) {
        if (!isFarmMap(currentMap)) return;
        if (plantedSeeds != null && plantGrowth != null) {
            plantedSeeds[col][row] = null;
            plantGrowth[col][row] = 0;
            mapTileNum[col][row] = 7;
        }
    }

    public void initializePlantTracking() {
        if (!isFarmMap(currentMap)) {
            return;
        }
        
        if (plantGrowth == null) {
            plantGrowth = new int[maxWorldCol][maxWorldRow];
        }
        
        if (wateredToday == null) {
            wateredToday = new boolean[maxWorldCol][maxWorldRow];
        }
        
        if (plantedSeeds == null) {
            plantedSeeds = new seed[maxWorldCol][maxWorldRow];
        }
    }

    public boolean isValidPosition(int col, int row) {
        return col >= 0 && row >= 0 && col < maxWorldCol && row < maxWorldRow;
    }

    public void setBrightness(float brightness) {
        this.brightness = Math.max(0.0f, Math.min(1.0f, brightness));
    }

    public float getBrightness() {
        return brightness;
    }

    public void drawBrightnessOverlay(Graphics2D g2) {
        if (brightness < 1.0f) {
            AlphaComposite originalComposite = (AlphaComposite) g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - brightness));
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setComposite(originalComposite);
        }
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void debugAllPlants() {
        if (!isFarmMap(currentMap) || plantedSeeds == null)
            return;
        
        int totalPlants = 0;
        
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                if (plantedSeeds[col][row] != null) {
                    System.out.println("Plant at (" + col + "," + row + "): " + 
                                       plantedSeeds[col][row].getName() + 
                                       " - Growth: " + plantGrowth[col][row] + 
                                       "/" + plantedSeeds[col][row].getGrowthTime());
                    totalPlants++;
                }
            }
        }
        System.out.println("Total plants found: " + totalPlants);
        System.out.println("Current season: " + gp.timeM.getSeason());
    }
}