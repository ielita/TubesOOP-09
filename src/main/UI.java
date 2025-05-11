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
    Graphics2D g2;
    Font arial_40;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Chest chest = new OBJ_Chest(gp);
        chestImage = chest.image; 
    }

    public void draw(Graphics2D g2){
        

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);


        g2.drawImage(chestImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString(" SPAKBOR SI PETANI", 90, 80);



        if (gp.gameState == gp.playState){

        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
    }

    public void drawPauseScreen(){


        String text = "PAUSED";
        int x = getXforCenteredText(text); ;
        

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    
    }

    public int getXforCenteredText(String text){
        int length =  (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }
}