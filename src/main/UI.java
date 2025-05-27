package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import object.OBJ_Chest;
import object.OBJ_Door;
import tile.MapManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import items.*;

public class UI {

    private Font arial_80;
    private Font arial_40;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;
    MapManager mapM = new MapManager(gp);

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

            // Draw current map name
            String currentMap = gp.mapM.getCurrentMap();
            String mapText = "Location: " + currentMap.substring(0, 1).toUpperCase() + currentMap.substring(1);
            g2.drawString(mapText, 50, 100);  // Below the date
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if (gp.gameState == gp.inventoryState) {
            drawInventory();
        }

        if (gp.gameState == gp.fishingMiniGameState) {
            drawFishingMiniGame();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Player World X: " + gp.player.worldX, 10, 400);
            g2.drawString("Player World Y: " + gp.player.worldY, 10, 420);
            g2.drawString("Col: " + gp.player.worldX / gp.tileSize, 10, 440);
            g2.drawString("Row: " + gp.player.worldY / gp.tileSize, 10, 460);
            g2.drawString("Energy: " + gp.player.getEnergy(), 10, 480);  // Add energy display
            g2.drawString("Gold: " + gp.player.getGold(), 10, 500);      // Add gold display
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            g2.drawString("Interact Pressed: " + interactState, 10, 520); // Move down

            // Add selected item display
            String selectedItem = "Selected Item: None";
            if (gp.player.getOnhandItem() != null) {
                selectedItem = "Selected Item: " + gp.player.getOnhandItem().getName();
            }
            g2.drawString(selectedItem, 10, 540); // Move down

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

    public void drawInventory() {
        int invWidth = 500;
        int invHeight = 300;
        int invX = gp.screenWidth / 2 - invWidth / 2;
        int invY = gp.screenHeight / 2 - invHeight / 2;
        g2.setColor(new Color(30, 30, 30, 220));
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);

        g2.setColor(Color.WHITE);
        g2.setFont(arial_40);
        String title = "Inventory";
        int titleX = invX + (invWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, invY + 40);

        int cols = 5;
        int slotSize = 64;
        int slotGap = 20;
        int startX = invX + 40;
        int startY = invY + 60;

        List<Map.Entry<Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
        entries.sort((a, b) -> {
            boolean aEquip = a.getKey() instanceof equipment;
            boolean bEquip = b.getKey() instanceof equipment;
            if (aEquip && !bEquip) return -1;
            if (!aEquip && bEquip) return 1;
            return 0;
        });

        for (int i = 0; i < entries.size(); i++) {
            Item item = entries.get(i).getKey();
            int quantity = entries.get(i).getValue();
            int col = i % cols;
            int row = i / cols;
            int x = startX + col * (slotSize + slotGap);
            int y = startY + row * (slotSize + 40);

            if (i == gp.keyH.inventoryCursorIndex) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(3));
                g2.drawRoundRect(x-2, y-2, slotSize+4, slotSize+4, 12, 12);
            }

            g2.setColor(new Color(80, 80, 80, 200));
            g2.fillRoundRect(x, y, slotSize, slotSize, 10, 10);

            if (item.getImage() != null) {
                g2.drawImage(item.getImage(), x + 8, y + 8, slotSize - 16, slotSize - 16, null);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            String itemName = item.getName();
            int nameWidth = g2.getFontMetrics().stringWidth(itemName);
            g2.drawString(itemName, x + (slotSize - nameWidth) / 2, y + slotSize + 15);

            String qtyText = "x" + quantity;
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            int qtyWidth = g2.getFontMetrics().stringWidth(qtyText);
            g2.drawString(qtyText, x + slotSize - qtyWidth - 6, y + slotSize - 6);
        }
    }
        
    public void drawFishingMiniGame() {
        int boxWidth = 420;
        int boxHeight = 260;
        int boxX = gp.screenWidth / 2 - boxWidth / 2;
        int boxY = gp.screenHeight / 2 - boxHeight / 2;

        // Background box
        g2.setColor(new Color(30, 30, 60, 230));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(arial_40);
        String title = "Mini Game: Fishing!";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, boxY + 50);

        // Instruction
        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        String instr = "Tebak angka " + gp.fishingMiniGame.getMin() + "-" + gp.fishingMiniGame.getMax() + " untuk dapat ikan!";
        int instrX = getXforCenteredText(instr);
        g2.drawString(instr, instrX, boxY + 120);

        // Sisa tries
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String triesText = "Sisa kesempatan: " + gp.fishingMiniGame.getTries();
        int triesX = getXforCenteredText(triesText);
        g2.drawString(triesText, triesX, boxY + 150);

        // Input box
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        String inputText = "Input: ";
        int inputVal = gp.fishingMiniGame.getInput();
        if (inputVal != 0) {
            inputText += inputVal;
        } else {
            inputText += "_";
        }
        int inputX = getXforCenteredText(inputText);
        int inputBoxY = boxY + 200;
        g2.setColor(new Color(60, 60, 100, 180));
        g2.fillRoundRect(inputX - 30, inputBoxY - 36, 220, 48, 16, 16);
        g2.setColor(Color.WHITE);
        g2.drawString(inputText, inputX, inputBoxY);
    }
            
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}