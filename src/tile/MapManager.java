package tile;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import main.GamePanel;
import items.seed;

public class MapManager {
    GamePanel gp;
    public String currentMap;
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

    public MapManager(GamePanel gp) {
        this.gp = gp;
        currentMap = "insideHouse";
        loadMapConfig(currentMap);
    }
    
    public void loadMapConfig(String mapName) {
        try {
            FileInputStream fis = new FileInputStream("res/maps/" + mapName + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String[] dimensions = br.readLine().split(" ");
            maxWorldCol = Integer.parseInt(dimensions[0]);
            maxWorldRow = Integer.parseInt(dimensions[1]);
            initializeArrays();
            loadMap(br);
            br.close();
        } catch(Exception e) {
        }
    }
    
    private void initializeArrays() {
        mapTileNum = new int[maxWorldCol][maxWorldRow];
        if ("farm".equals(currentMap)) {
            plantGrowth = new int[maxWorldCol][maxWorldRow];
            wateredToday = new boolean[maxWorldCol][maxWorldRow];
            plantedSeeds = new seed[maxWorldCol][maxWorldRow];
        }
    }
    
    private void loadMap(BufferedReader br) {
        try {
            int col = 0, row = 0;
            while(row < maxWorldRow) {
                String line = br.readLine();
                if(line == null) break;
                String numbers[] = line.split(" ");
                while(col < maxWorldCol && col < numbers.length) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }
                if(col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch(Exception e) {
        }
    }
    
    public void changeMap(String newMap, int playerX, int playerY) {
        if ("farm".equals(currentMap)) {
            saveFarmState();
        }
        currentMap = newMap;
        loadMapConfig(newMap);
        if ("farm".equals(newMap)) {
            restoreFarmState();
        }
        gp.aSetter.setObject(newMap);
        gp.player.setPosition(playerX, playerY);
    }

    public void rainyDay() {
        if (gp.timeM.isRainyDay()) {
            if ("farm".equals(currentMap) && wateredToday != null) {
                for (int col = 0; col < maxWorldCol; col++) {
                    for (int row = 0; row < maxWorldRow; row++) {
                        int currentTile = mapTileNum[col][row];

                        if (currentTile == 7 || currentTile == 8){
                            wateredToday[col][row] = true;
                        }

                        if (currentTile == 8) { 
                            mapTileNum[col][row] = 10;
                        } else if (currentTile == 7) {
                            mapTileNum[col][row] = 9;
                        }
                    }
                }
            }
            if (!"farm".equals(currentMap) && savedFarmWatered != null) {
                for (int col = 0; col < savedFarmWatered.length; col++) {
                    for (int row = 0; row < savedFarmWatered[col].length; row++) {
                        if (savedFarmTiles != null) {
                            int currentTile = savedFarmTiles[col][row];

                        if (currentTile == 7 || currentTile == 8){
                            savedFarmWatered[col][row] = true;
                        }

                            if (currentTile == 8) {
                                savedFarmTiles[col][row] = 10;
                            } else if (currentTile == 7) {
                                savedFarmTiles[col][row] = 9;
                            }
                        }
                    }
                }
            }
        } else {
        }
    }
    
    private void saveFarmState() {
        if (mapTileNum == null) return;
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
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) { 
                mapTileNum[col][row] = savedFarmTiles[col][row];
            }
        }
        if (savedFarmGrowth != null) {
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    plantGrowth[col][row] = savedFarmGrowth[col][row];
                    wateredToday[col][row] = savedFarmWatered[col][row];
                    plantedSeeds[col][row] = savedFarmSeeds[col][row];
                }
            }
        }
    }
    public void updatePlantGrowth() {
        if (!"farm".equals(currentMap) || plantGrowth == null) {
            if (savedFarmGrowth != null) {
                updateSavedFarmState();
            }
            return;
        }
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                int currentTile = mapTileNum[col][row];
                if (currentTile == 9) {
                    mapTileNum[col][row] = 7;
                } else if (currentTile == 10) {
                    mapTileNum[col][row] = 8;
                    if (wateredToday[col][row] 
                    && plantGrowth[col][row] > 0 
                    && plantedSeeds[col][row] != null 
                    && plantedSeeds[col][row].getSeason().equalsIgnoreCase(gp.timeM.getSeason())) {
                        plantGrowth[col][row]--;
                        if (plantGrowth[col][row] <= 0) {
                            mapTileNum[col][row] = 11;
                        }
                    }
                }
                wateredToday[col][row] = false;
            }
        }
    }
    
    private void updateSavedFarmState() {
        for (int col = 0; col < savedFarmTiles.length; col++) {
            for (int row = 0; row < savedFarmTiles[col].length; row++) {
                int currentTile = savedFarmTiles[col][row];
                if (currentTile == 9) {
                    savedFarmTiles[col][row] = 7;
                } else if (currentTile == 10) {
                    savedFarmTiles[col][row] = 8;
                    if (savedFarmWatered[col][row] 
                    && savedFarmGrowth[col][row] > 0 
                    && savedFarmSeeds[col][row] != null 
                    && savedFarmSeeds[col][row].getSeason().equalsIgnoreCase(gp.timeM.getSeason())) {
                        savedFarmGrowth[col][row]--;
                        if (savedFarmGrowth[col][row] <= 0) {
                            savedFarmTiles[col][row] = 11;
                        }
                    }
                }
                savedFarmWatered[col][row] = false;
            }
        }
    }
    
    public void harvestCrop(int col, int row) {
        if (!"farm".equals(currentMap)) return;
        if (plantedSeeds != null && plantGrowth != null) {
            plantedSeeds[col][row] = null;
            plantGrowth[col][row] = 0;
            mapTileNum[col][row] = 7;
        }
    }
    
    public void initializePlantTracking() {
        if (!"farm".equals(currentMap)) return;
        if (maxWorldCol > 0 && maxWorldRow > 0) {
            plantedSeeds = new seed[maxWorldCol][maxWorldRow];
            plantGrowth = new int[maxWorldCol][maxWorldRow];
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
            Color originalColor = g2.getColor();
            g2.setColor(new Color(0, 0, 0, 1.0f - brightness));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - brightness));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setComposite(originalComposite);
            g2.setColor(originalColor);
        }
    }
    
    public String getCurrentMap() {
        return currentMap;
    }
}