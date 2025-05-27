package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class NPC_clone extends Entity{

    public NPC_clone(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1; 
        getImage();
    }

    public void getImage() {

        up1 = setup("npc/abigail/abigail-idle1.png");
    }


    public void draw(Graphics2D g2){

        BufferedImage image = null;
        image = up1;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
        }
    
}