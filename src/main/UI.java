package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Color;
import object.OBJ_Chest;


public class UI{

    BufferedImage chestImage;

    GamePanel gp;
    Font arial_40;

    public UI (GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Chest chest = new OBJ_Chest(gp);
        chestImage = chest.image; 
    }

    public void draw(Graphics2D g2){
        
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(chestImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString(" SPAKBOR SI PETANI", 90, 70);

    }
}