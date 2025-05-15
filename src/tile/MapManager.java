package tile;
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
        previousMap = currentMap;
        loadMapConfig(newMap);
        gp.player.worldX = playerX * gp.tileSize;
        gp.player.worldY = playerY * gp.tileSize;
    }
}