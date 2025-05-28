package tile;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import main.GamePanel;
import items.seed; // Add this import

public class MapManager {
    GamePanel gp;
    public String currentMap;
    public String previousMap;
    public int[][] mapTileNum;
    public int maxWorldCol;
    public int maxWorldRow;
    private float brightness = 1.0f;
    public int[][] plantGrowth;
    public boolean[][] wateredToday;
    
    // Add these to store map states
    public java.util.Map<String, int[][]> savedMapStates;
    public java.util.Map<String, int[][]> savedPlantGrowth;
    public java.util.Map<String, boolean[][]> savedWateredStates;
    
    // Add these fields for seed planting system
    public seed[][] plantedSeeds;  // Track what seed is planted where

    public MapManager(GamePanel gp) {
        this.gp = gp;
        previousMap = null;    
        currentMap = "insideHouse";
        
        // Initialize the save state maps
        savedMapStates = new java.util.HashMap<>();
        savedPlantGrowth = new java.util.HashMap<>();
        savedWateredStates = new java.util.HashMap<>();
        
        loadMapConfig(currentMap);
    }
    
    public void loadMapConfig(String mapName) {
        try {
            FileInputStream fis = new FileInputStream("res/maps/" + mapName + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            
            // Read map dimensions from first line
            String[] dimensions = br.readLine().split(" ");
            maxWorldCol = Integer.parseInt(dimensions[0]);
            maxWorldRow = Integer.parseInt(dimensions[1]);
            System.out.println("Map dimensions: " + maxWorldCol + " x " + maxWorldRow);

            
            mapTileNum = new int[maxWorldCol][maxWorldRow];
            plantGrowth = new int[maxWorldCol][maxWorldRow];
            wateredToday = new boolean[maxWorldCol][maxWorldRow]; // Initialize watering array
            loadMap(br);
            
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadMap(BufferedReader br) {
        try {
            int col = 0;
            int row = 0;
            
            while(row < maxWorldRow) {
                String line = br.readLine();
                if(line == null) break;
                
                String numbers[] = line.split(" ");
                
                while(col < maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
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
        // Save current map state before changing
        saveCurrentMapState();
        
        currentMap = newMap;
        loadMapConfig(newMap);
        
        // Restore saved state if it exists
        restoreMapState(newMap);
        
        gp.aSetter.setObject(newMap);
        gp.player.setPosition(playerX, playerY);
        System.out.println("Changed to map: " + currentMap);
        System.out.println("Player spawned at: " + playerX + "," + playerY);
    }
    
    private void saveCurrentMapState() {
        if (currentMap != null && mapTileNum != null) {
            // Deep copy the current map state
            int[][] mapCopy = new int[maxWorldCol][maxWorldRow];
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    mapCopy[col][row] = mapTileNum[col][row];
                }
            }
            savedMapStates.put(currentMap, mapCopy);
            
            // Save plant growth state
            if (plantGrowth != null) {
                int[][] growthCopy = new int[maxWorldCol][maxWorldRow];
                for (int col = 0; col < maxWorldCol; col++) {
                    for (int row = 0; row < maxWorldRow; row++) {
                        growthCopy[col][row] = plantGrowth[col][row];
                    }
                }
                savedPlantGrowth.put(currentMap, growthCopy);
            }
            
            // Save watered state
            if (wateredToday != null) {
                boolean[][] wateredCopy = new boolean[maxWorldCol][maxWorldRow];
                for (int col = 0; col < maxWorldCol; col++) {
                    for (int row = 0; row < maxWorldRow; row++) {
                        wateredCopy[col][row] = wateredToday[col][row];
                    }
                }
                savedWateredStates.put(currentMap, wateredCopy);
            }
            
            System.out.println("Saved state for map: " + currentMap);
        }
    }
    
    private void restoreMapState(String mapName) {
        // Restore map tiles if saved state exists
        if (savedMapStates.containsKey(mapName)) {
            int[][] savedMap = savedMapStates.get(mapName);
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    mapTileNum[col][row] = savedMap[col][row];
                }
            }
            System.out.println("Restored tile state for map: " + mapName);
        }
        
        // Restore plant growth if saved state exists
        if (savedPlantGrowth.containsKey(mapName)) {
            int[][] savedGrowth = savedPlantGrowth.get(mapName);
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    plantGrowth[col][row] = savedGrowth[col][row];
                }
            }
            System.out.println("Restored plant growth for map: " + mapName);
        }
        
        // Restore watered state if saved state exists
        if (savedWateredStates.containsKey(mapName)) {
            boolean[][] savedWatered = savedWateredStates.get(mapName);
            for (int col = 0; col < maxWorldCol; col++) {
                for (int row = 0; row < maxWorldRow; row++) {
                    wateredToday[col][row] = savedWatered[col][row];
                }
            }
            System.out.println("Restored watered state for map: " + mapName);
        }
    }
    
    public String getCurrentMap() {
        return currentMap;
    }

    public void setBrightness(float brightness) {
        this.brightness = Math.max(0.0f, Math.min(1.0f, brightness));
    }

    public float getBrightness() {
        return brightness;
    }

    public void drawBrightnessOverlay(Graphics2D g2) {
        if (brightness < 1.0f) {
            // Store original composite
            AlphaComposite originalComposite = (AlphaComposite) g2.getComposite();
            Color originalColor = g2.getColor();

            // Set dark overlay
            g2.setColor(new Color(0, 0, 0, 1.0f - brightness));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - brightness));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // Restore original settings
            g2.setComposite(originalComposite);
            g2.setColor(originalColor);
        }
    }

    // Add this method to initialize plant tracking arrays
    public void initializePlantTracking() {
        if (maxWorldCol > 0 && maxWorldRow > 0) {
            plantedSeeds = new seed[maxWorldCol][maxWorldRow];
            plantGrowth = new int[maxWorldCol][maxWorldRow];
        }
    }

    // Update this method to handle daily resets properly
    public void updatePlantGrowth() {
        if (plantGrowth == null) return;
        
        System.out.println("=== Daily Plant Growth Update ===");
        
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                int currentTile = mapTileNum[col][row];
                
                // Reset watered tiles to dry at start of new day
                if (currentTile == 9) { // watered tilled -> dry tilled
                    mapTileNum[col][row] = 7;
                    System.out.println("Watered tilled soil dried at " + col + "," + row);
                } else if (currentTile == 10) { // watered planted -> dry planted
                    mapTileNum[col][row] = 8;
                    System.out.println("Watered planted soil dried at " + col + "," + row);
                    
                    // HANYA UPDATE GROWTH JIKA TANAMAN DI-WATER KEMARIN
                    if (wateredToday != null && wateredToday[col][row]) {
                        if (plantGrowth[col][row] > 0) {
                            plantGrowth[col][row]--;
                            System.out.println("Plant at " + col + "," + row + " grew! Days left: " + plantGrowth[col][row]);
                            
                            // Check if ready to harvest
                            if (plantGrowth[col][row] <= 0) {
                                mapTileNum[col][row] = 11; // Fully grown
                                System.out.println("Crop ready to harvest at " + col + "," + row + "!");
                            }
                        }
                    } else {
                        System.out.println("Plant at " + col + "," + row + " didn't grow (not watered yesterday)");
                    }
                }
                
                // Reset watered status for all tiles
                if (wateredToday != null) {
                    wateredToday[col][row] = false;
                }
                
                // Update plant growth HANYA untuk planted tiles yang di-water kemarin
                // (sudah di-handle di atas untuk tile 10 -> 8)
            }
        }
        
        System.out.println("=== Daily Update Complete ===");
    }

    // Add method to reset planted seed data when harvested
    public void harvestCrop(int col, int row) {
        if (plantedSeeds != null && plantGrowth != null) {
            plantedSeeds[col][row] = null;
            plantGrowth[col][row] = 0;
            mapTileNum[col][row] = 7; // Back to tilled soil
        }
    }

    // Add method to update all saved map states for daily changes
    public void updateAllSavedMapStates() {
        if (savedMapStates == null) return;
        
        for (String mapName : savedMapStates.keySet()) {
            System.out.println("Updating saved state for: " + mapName);
            
            // Get saved states for this map
            int[][] savedMap = savedMapStates.get(mapName);
            int[][] savedGrowth = savedPlantGrowth.get(mapName);
            boolean[][] savedWatered = savedWateredStates.get(mapName);
            
            if (savedMap == null || savedGrowth == null || savedWatered == null) continue;
            
            // Update each tile in the saved state
            for (int col = 0; col < savedMap.length; col++) {
                for (int row = 0; row < savedMap[col].length; row++) {
                    int currentTile = savedMap[col][row];
                    
                    // Reset watered tiles to dry
                    if (currentTile == 9) { // watered tilled -> dry tilled
                        savedMap[col][row] = 7;
                    } else if (currentTile == 10) { // watered planted -> dry planted
                        savedMap[col][row] = 8;
                        
                        // HANYA UPDATE GROWTH JIKA DI-WATER KEMARIN
                        if (savedWatered[col][row]) {
                            if (savedGrowth[col][row] > 0) {
                                savedGrowth[col][row]--;
                                
                                // Check if ready to harvest
                                if (savedGrowth[col][row] <= 0) {
                                    savedMap[col][row] = 11; // Fully grown
                                }
                            }
                        }
                    }
                    
                    // Reset watered status
                    savedWatered[col][row] = false;
                }
            }
        }
    }
}