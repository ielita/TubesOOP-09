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
    
    // Plant tracking arrays - hanya untuk farm
    public int[][] plantGrowth;
    public boolean[][] wateredToday;
    public seed[][] plantedSeeds;
    
    // Save state hanya untuk farm
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
            e.printStackTrace();
        }
    }
    
    private void initializeArrays() {
        mapTileNum = new int[maxWorldCol][maxWorldRow];
        
        // Hanya initialize plant arrays jika di farm
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
            e.printStackTrace();
        }
    }
    
    public void changeMap(String newMap, int playerX, int playerY) {
        // Save farm state jika leaving farm
        if ("farm".equals(currentMap)) {
            saveFarmState();
        }
        
        currentMap = newMap;
        loadMapConfig(newMap);
        
        // Restore farm state jika entering farm
        if ("farm".equals(newMap)) {
            restoreFarmState();
        }
        
        gp.aSetter.setObject(newMap);
        gp.player.setPosition(playerX, playerY);
        System.out.println("Changed to map: " + currentMap);
    }
    
    private void saveFarmState() {
        if (mapTileNum == null) return;
        
        // Save tiles
        savedFarmTiles = new int[maxWorldCol][maxWorldRow];
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                savedFarmTiles[col][row] = mapTileNum[col][row];
            }
        }
        
        // Save plant data if exists
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
        
        System.out.println("Farm state saved");
    }
    
    private void restoreFarmState() {
        if (savedFarmTiles == null) return;
        
        // Restore tiles
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                mapTileNum[col][row] = savedFarmTiles[col][row];
            }
        }
        
        // Restore plant data if exists
        if (savedFarmGrowth != null) {
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    plantGrowth[col][row] = savedFarmGrowth[col][row];
                    wateredToday[col][row] = savedFarmWatered[col][row];
                    plantedSeeds[col][row] = savedFarmSeeds[col][row];
                }
            }
        }
        
        System.out.println("Farm state restored");
    }
    
    // Daily update hanya untuk farm
    public void updatePlantGrowth() {
        if (!"farm".equals(currentMap) || plantGrowth == null) {
            // Update saved farm state jika sedang tidak di farm
            if (savedFarmGrowth != null) {
                updateSavedFarmState();
            }
            return;
        }
        
        System.out.println("=== Daily Farm Update ===");
        
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                int currentTile = mapTileNum[col][row];
                
                // Reset watered tiles
                if (currentTile == 9) {
                    mapTileNum[col][row] = 7;
                } else if (currentTile == 10) {
                    mapTileNum[col][row] = 8;
                    
                    // Update growth hanya jika di-water kemarin
                    if (wateredToday[col][row] && plantGrowth[col][row] > 0) {
                        plantGrowth[col][row]--;
                        if (plantGrowth[col][row] <= 0) {
                            mapTileNum[col][row] = 11; // Ready to harvest
                        }
                    }
                }
                
                // Reset watered status
                wateredToday[col][row] = false;
            }
        }
        
        System.out.println("=== Farm Update Complete ===");
    }
    
    private void updateSavedFarmState() {
        System.out.println("=== Updating Saved Farm State ===");
        
        for (int col = 0; col < savedFarmTiles.length; col++) {
            for (int row = 0; row < savedFarmTiles[col].length; row++) {
                int currentTile = savedFarmTiles[col][row];
                
                // Reset watered tiles
                if (currentTile == 9) {
                    savedFarmTiles[col][row] = 7;
                } else if (currentTile == 10) {
                    savedFarmTiles[col][row] = 8;
                    
                    // Update growth hanya jika di-water kemarin
                    if (savedFarmWatered[col][row] && savedFarmGrowth[col][row] > 0) {
                        savedFarmGrowth[col][row]--;
                        if (savedFarmGrowth[col][row] <= 0) {
                            savedFarmTiles[col][row] = 11;
                        }
                    }
                }
                
                // Reset watered status
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
    
    // Brightness methods (unchanged)
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