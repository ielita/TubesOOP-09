package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import object.OBJ_Chest;
import object.OBJ_Door;


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

        if(gp.keyH.showDebug) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Player World X: " + gp.player.worldX, 10, 400);
            g2.drawString("Player World Y: " + gp.player.worldY, 10, 420);
            g2.drawString("Col: " + gp.player.worldX/gp.tileSize, 10, 440);
            g2.drawString("Row: " + gp.player.worldY/gp.tileSize, 10, 460);
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            g2.drawString("Interact Pressed: " + interactState, 10, 480);

            // Draw door messages
            for(int i = 0; i < gp.obj.length; i++) {
                if(gp.obj[i] != null && gp.obj[i] instanceof OBJ_Door) {
                    OBJ_Door door = (OBJ_Door)gp.obj[i];
                    g2.drawString(door.getMessage(), 10, gp.screenHeight - 50);
                }
            }
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