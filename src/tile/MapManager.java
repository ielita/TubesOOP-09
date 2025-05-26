package tile;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import main.GamePanel;

public class MapManager {
    GamePanel gp;
    public String currentMap;
    public String previousMap;
    public int[][] mapTileNum;
    public int maxWorldCol;
    public int maxWorldRow;
    private float brightness = 1.0f; // 0.0f = dark, 1.0f = normal brightness
    public int[][] plantGrowth; // Add this line
    public boolean[][] wateredToday; // Track if tile was watered today
    
    public MapManager(GamePanel gp) {
        this.gp = gp;
        previousMap = null;    
        currentMap = "insideHouse";
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
        currentMap = newMap;
        loadMapConfig(newMap);
        gp.aSetter.setObject(newMap);
        gp.player.setPosition(playerX, playerY);  // Use the new method
        System.out.println("Player spawned at: " + playerX + "," + playerY); // Debug print
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

    public void updatePlantGrowth() {
        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                // Only reduce growth time if plant exists AND was watered today
                if (plantGrowth[col][row] > 0 && wateredToday[col][row]) {
                    plantGrowth[col][row]--;
                    if (plantGrowth[col][row] == 0) {
                        // Change to harvested tile
                        mapTileNum[col][row] = 11; // harvested
                        System.out.println("Crop ready to harvest at col:" + col + " row:" + row);
                    }
                }
                
                // Reset watering status for new day and dry out tiles
                wateredToday[col][row] = false;
                
                // Convert watered tiles back to dry versions
                if (mapTileNum[col][row] == 9) { // tilted_w -> tilted
                    mapTileNum[col][row] = 7;
                } else if (mapTileNum[col][row] == 10) { // planted_w -> planted
                    mapTileNum[col][row] = 8;
                }
            }
        }
    }
}