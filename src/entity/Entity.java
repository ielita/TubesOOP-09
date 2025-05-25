package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Rectangle;
import main.GamePanel;
import java.io.IOException;
import java.awt.Graphics2D;
import main.UtilityTool;
import javax.imageio.ImageIO;

public class Entity{

    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1,up2, down1, down2, left1, left2, right1, right2, base;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public Entity(GamePanel gp){        
        this.gp = gp;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;


        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }
    
    public BufferedImage  setup(String imagePath){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File("res/" + imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}