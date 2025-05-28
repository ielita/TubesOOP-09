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
    private Font arial_50;
    private Font arial_40;
    private Font arial_30;
    private Font arial_20;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;

    public int commandNum = 0;
    public int keyBindNum = 0; 

    MapManager mapM = new MapManager(gp);

    int subState = 0; 

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_80 = new Font("Arial", Font.BOLD, 80);
        arial_50 = new Font("Arial", Font.PLAIN, 50);
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Chest chest = new OBJ_Chest(gp);
        chestImage = chest.image;
        
    }
    
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if (gp.gameState == gp.playState) {
        }

        if (gp.gameState == gp.menuState) {
            drawMainMenu();
        }

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            g2.drawImage(chestImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            // Draw time in top-right corner
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            String time = gp.timeM.getTimeString();
            g2.drawString(time, gp.screenWidth - 150, 50);

            // Draw season and day
            String season = gp.timeM.getSeason();
            int day = gp.timeM.getDay();
            String dateText = season + " - Day " + day;
            g2.drawString(dateText, 100, 50);  // Top left corner

            // Draw current map name
            String currentMap = gp.mapM.getCurrentMap();
            String mapText = "Location: " + currentMap.substring(0, 1).toUpperCase() + currentMap.substring(1);
            g2.drawString(mapText, 100, 100);  // Below the date
            

        }

        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        if (gp.gameState == gp.inventoryState) {
            drawInventory();
        }

        if (gp.gameState == gp.keyBindingState) {
            drawKeyBindings();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int yOffset = 200; // Starting Y offset for debug info
            g2.drawString("Player World X: " + gp.player.worldX, 10, yOffset);
            yOffset += gp.tileSize / 2;
            g2.drawString("Player World Y: " + gp.player.worldY, 10, yOffset);
            yOffset += gp.tileSize / 2;
            g2.drawString("Col: " + gp.player.worldX / gp.tileSize, 10, yOffset);
            yOffset += gp.tileSize / 2;
            g2.drawString("Row: " + gp.player.worldY / gp.tileSize, 10, yOffset);
            yOffset += gp.tileSize / 2;
            g2.drawString("Energy: " + gp.player.getEnergy(), 10, yOffset);  // Add energy display
            yOffset += gp.tileSize / 2;
            g2.drawString("Gold: " + gp.player.getGold(), 10, yOffset);      // Add gold display
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            yOffset += gp.tileSize / 2;
            g2.drawString("Interact Pressed: " + interactState, 10, yOffset); // Move down
            
            // Add selected item display
            String selectedItem = "Selected Item: None";
            if (gp.player.getOnhandItem() != null) {
                selectedItem = "Selected Item: " + gp.player.getOnhandItem().getName();
            }
            yOffset += gp.tileSize / 2;
            g2.drawString(selectedItem, 10, yOffset); // Move down

            // Draw door messages
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] != null && gp.obj[i] instanceof OBJ_Door) {
                    OBJ_Door door = (OBJ_Door) gp.obj[i];
                    g2.drawString(door.getMessage(), 10, gp.screenHeight - 50);
                }
            }
        }
    }


    private void drawsubWindow(int frameX, int frameY, int frameWidth, int frameHeight) {
        Color backgroundColor = new Color(0, 0, 0, 180); // Semi-transparent black
        g2.setColor(backgroundColor);
        g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);

        Color borderColor = new Color(255, 255, 255);
        g2.setColor(borderColor);
        g2.setStroke(new java.awt.BasicStroke(5));
        g2.drawRoundRect(frameX + 5, frameY + 5, frameWidth - 10, frameHeight - 10, 25, 25);
    }

    public void drawOptionsScreen() {


        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 6;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 - 1 * gp.tileSize; 
        drawsubWindow(frameX, frameY, frameWidth, frameHeight);


        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: break;
            case 3: break;
            case 4:break;
            }
        
        gp.keyH.enterPressed = false; 

  
    }

    public void options_top(int frameX, int frameY){
        int textX;
        int textY; 

        g2.setFont(arial_50);
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize ;
        g2.drawString(text, textX, textY);
        
        textX = frameY + 5 * gp.tileSize - 25;
        
        g2.setFont(arial_30);
        
        textY += gp.tileSize * 7 / 8;
        g2.drawString("Fullscreen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                if(gp.fullScreenOn == true) {
                    gp.fullScreenOn = false;
                    gp.setFullScreen();
                } 
                else if (gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                    gp.setFullScreen();
                }
            }
        }

        textY += gp.tileSize *6/10 ;
        g2.drawString("Backsound", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.backsoundOn = !gp.backsoundOn;
                if (gp.backsoundOn) {
                    gp.playMusic(0); // Play background music
                } else {
                    gp.stopMusic(); // Stop background music
                } 
            } 
        }

        textY += gp.tileSize *6/10 ;
        g2.drawString("Key Bindings", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.keyBindingState; // Switch to key binding state
                gp.keyH.enterPressed = false; 
                commandNum = 0; 
            }
        }
        
        textY += gp.tileSize *6/10 ;
        g2.drawString("Main Menu", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.menuState;
                gp.keyH.enterPressed = false; 
                gp.stopMusic();
            }
            
        }
        
        textY += gp.tileSize *6/10 ;
        g2.drawString("Return to Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                gp.keyH.enterPressed = false; 
            }
        }
        
        
        textX = frameY + 9 * gp.tileSize ;
        textY = frameY + gp.tileSize * 15/8 ;

        if (gp.fullScreenOn) {
            g2.drawString("yes", textX, textY);
        } else {
            g2.drawString("no", textX, textY);
        }
        
        textY += gp.tileSize *6/10;

        if (gp.backsoundOn) {
            g2.drawString("yes", textX, textY);
        } else {
            g2.drawString("no", textX, textY);
        }
        
    }
    


    public void drawKeyBindings() {
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 7;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 ; 
        drawsubWindow(frameX, frameY, frameWidth, frameHeight);
        
        g2.setFont(arial_40);
        String text = "KEY BINDINGS";
        int textX = getXforCenteredText(text);
        int textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        g2.setFont(arial_30);
        textX = frameX +  gp.tileSize - 25;
        
        textY += gp.tileSize * 7 / 8;
        g2.drawString("Move Up", textX, textY);
        // if (keyBindNum == 0) {
        //     g2.drawString(">", textX -18, textY);   
        // }
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Down", textX, textY);
        // if (keyBindNum == 1) {
        //     g2.drawString(">", textX -18, textY);   
        // }
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Left", textX, textY);
        // if (keyBindNum == 2) {
        //     g2.drawString(">", textX -18, textY);   
        // }
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Right", textX, textY);
        // if (keyBindNum == 3) {
        //     g2.drawString(">", textX -18, textY);   
        // }
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Run", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("Inventory", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("Interact", textX, textY);
        // if (keyBindNum == 4) {
        //     g2.drawString(">", textX -18, textY);   
        // }
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Back", textX, textY);
        if (keyBindNum == 0) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.optionsState;
                gp.keyH.enterPressed = false; 
                gp.stopMusic();
            }
            
        }

        
        textX = frameX +  5 * gp.tileSize;
        textY = frameY + gp.tileSize + gp.tileSize * 7 / 8;
        g2.drawString("w", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("s", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("a", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("d", textX, textY);

        textX = frameX +  5 * gp.tileSize - 25;
        textY += gp.tileSize * 6 / 10;
        g2.drawString("shift", textX, textY);
        textX = frameX +  5 * gp.tileSize;
        
        textY += gp.tileSize * 6 / 10;
        g2.drawString("j", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("f", textX, textY);

        // Add more key bindings as needed
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
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2 / 3;
        g2.setColor(gp.keyH.menuOption == 1 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        text = "CREDITS";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2 / 3;
        g2.setColor(gp.keyH.menuOption == 2 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        text = "EXIT";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2 / 3;
        g2.setColor(gp.keyH.menuOption == 3 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);


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

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}