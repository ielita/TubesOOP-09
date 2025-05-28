package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public MapManager mapManager;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[20];
        mapManager = new MapManager(gp);
        getTileImage();
    }

    public void getTileImage() {
        // Initialize all array elements first
        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
        }


        loadTile("tiledata");
}

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(new File("res/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadTile(String tileName) {
        try {
            FileInputStream fis = new FileInputStream("res/tilesconfig/" + tileName + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String idLine;
            while ((idLine = br.readLine()) != null) {
                idLine = idLine.trim();
                if (idLine.isEmpty()) continue; // Skip empty lines

                int id = Integer.parseInt(idLine);

                String imageLine = br.readLine();
                String collisionLine = br.readLine();

                if (imageLine == null || collisionLine == null) {
                    System.out.println("Incomplete data at ID " + id);
                    break;
                }

                String imageName = imageLine.trim();
                String baseName = imageName.replace(".png", "");
                boolean collision = Boolean.parseBoolean(collisionLine.trim());

                setup(id, baseName, collision);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < mapManager.maxWorldCol && worldRow < mapManager.maxWorldRow) {
            int tileNum = mapManager.mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == mapManager.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}