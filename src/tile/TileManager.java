package tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public MapManager mapManager;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapManager = new MapManager(gp);
        getTileImage();
    }

    public void getTileImage() {
        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
        }
        setup(0, "grass", false);
        setup(1, "land", false);
        setup(2, "tree", true);
        setup(3, "tree2", true);
        setup(4, "wall", true);
        setup(5, "wall2", true);
        setup(6, "water", true);
        setup(7, "tilted", false);
        setup(8, "planted", false);
        setup(9, "tilted_w", false);
        setup(10, "planted_w", false);
        setup(11, "harvest", false);
        setup(12, "woodtile", false);
        setup(13, "woodwallsideleft", true);
        setup(14, "woodwallsidedown", true);
        setup(15, "woodwallsideright", true);
        setup(16, "woodwallsideup", true);
        setup(17, "woodwallcorner1", true);
        setup(18, "woodwallcorner2", true);
        setup(19, "woodwallcorner3", true);
        setup(20, "woodwallcorner4", true);
        setup(21, "shippingbin1", true);
        setup(22, "shippingbin2", true);
        setup(23, "shippingbin3", true);
        setup(24, "shippingbin4", true);
        setup(25, "shippingbin5", true);
        setup(26, "shippingbin6", true);
<<<<<<< Updated upstream
    }
=======

        setup(31, "house1", true);
        setup(32, "house2", true);
        setup(33, "house3", true);
        setup(34, "house4", true);
        setup(35, "house5", true);
        setup(36, "house6", true);
        setup(37, "house7", true);
        setup(38, "house8", true);
        setup(39, "house9", true);
        setup(40, "house10", true);
        setup(41, "house11", true);
        setup(42, "house12", true);
        setup(43, "house13", true);
        setup(44, "house14", true);
        setup(45, "house15", true);
        setup(46, "house16", true);
        setup(47, "house17", true);
        setup(48, "house18", true);
        setup(49, "house19", true);
        setup(50, "house20", true);
        setup(51, "house21", true);
        setup(52, "house22", true);
        setup(53, "house23", true);
        setup(54, "house24", true);
        setup(55, "house25", true);
        setup(56, "house26", true);
        setup(57, "house27", true);
        setup(58, "house28", true);
        setup(59, "house29", true);
        setup(60, "house30", true);
        setup(61, "house31", true);
        setup(62, "house32", true);
        setup(63, "house33", true);
        setup(64, "house34", true);
        setup(65, "house35", true);
        setup(66, "house36", true);
        
}
>>>>>>> Stashed changes

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

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldRow < mapManager.maxWorldRow) {
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
