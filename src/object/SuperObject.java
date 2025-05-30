package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class SuperObject{

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 63, 63);
    public Rectangle triggerArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();
    KeyHandler keyH = new KeyHandler(gp);
    protected String message;

    public void draw(Graphics2D g2, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }

    protected boolean isPlayerInRange(GamePanel gp, int range) {
        int playerX = gp.player.worldX + gp.player.solidArea.x;
        int playerY = gp.player.worldY + gp.player.solidArea.y;
        int objectX = worldX + solidArea.x;
        int objectY = worldY + solidArea.y;
        
        int distance = (int)Math.sqrt(
            Math.pow(playerX - objectX, 2) + 
            Math.pow(playerY - objectY, 2)
        );
        
        return distance < range;
    }

    
    public void interact(GamePanel gp, KeyHandler keyH) {
        if (keyH != null) {
            int interactionRange = (int)(gp.tileSize * 1.5);
            boolean inRange = isPlayerInRange(gp, interactionRange);
            
            if (inRange && keyH.interactPressed) {
                onInteract();
            }
            
            message = String.format("Range: %b, Interact: %b", 
                inRange, keyH.interactPressed);
        } else {
            message = "Error: handlers not initialized";
        }
    }
    
    protected void onInteract() {}

    public void update() {}
    
}