package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import object.OBJ_Chest;
import object.OBJ_Door;

public class UI {

    private Font arial_80;
    private Font arial_40;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_80 = new Font("Arial", Font.BOLD, 80);
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Chest chest = new OBJ_Chest(gp);
        chestImage = chest.image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.menuState) {
            drawMainMenu();
        }

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);


        if (gp.gameState == gp.playState) {
            // Draw time in top-right corner
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            String time = gp.timeM.getTimeString();
            g2.drawString(time, gp.screenWidth - 150, 50);

            // Draw season and day
            String season = gp.timeM.getSeason();
            int day = gp.timeM.getDay();
            String dateText = season + " - Day " + day;
            g2.drawString(dateText, 50, 50);  // Top left corner
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Player World X: " + gp.player.worldX, 10, 400);
            g2.drawString("Player World Y: " + gp.player.worldY, 10, 420);
            g2.drawString("Col: " + gp.player.worldX / gp.tileSize, 10, 440);
            g2.drawString("Row: " + gp.player.worldY / gp.tileSize, 10, 460);
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            g2.drawString("Interact Pressed: " + interactState, 10, 480);

            // Draw door messages
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] != null && gp.obj[i] instanceof OBJ_Door) {
                    OBJ_Door door = (OBJ_Door) gp.obj[i];
                    g2.drawString(door.getMessage(), 10, gp.screenHeight - 50);
                }
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(arial_80);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        // Main text
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Menu options
        g2.setFont(arial_40);
        
        // Continue option
        text = "CONTINUE";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.setColor(gp.keyH.pauseOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        // Main Menu option
        text = "MAIN MENU";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(gp.keyH.pauseOption == 1 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);
    }

    public void drawMainMenu() {
        // Background
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title
        g2.setFont(arial_80);
        String text = "SPAKBOR SI PETANI";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        // Main text
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Menu options
        g2.setFont(arial_40);
        text = "START GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(gp.keyH.menuOption == 1 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        text = "EXIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.setColor(gp.keyH.menuOption == 2 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        // Draw chest icon
        g2.drawImage(chestImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}