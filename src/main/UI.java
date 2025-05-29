package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.awt.FontFormatException;
import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; // Tambahkan ini

import object.OBJ_Chest;
import object.OBJ_Door;
import tile.MapManager;

import java.util.ArrayList;
import java.util.List;

public class UI {

    private Font pixelify40;
    private Font pixelify26;
    private Font pixelify22;
    private Font pixelify36;
    private Font pixelify80;
    private Font pixelify120;
    private Font pixelify15;
    private Font pixelify50;
    private Font pixelify30;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;

    private int sleepAnimationTimer = 0;
    private int sleepAnimationStage = 0;
    private final int SLEEP_ANIMATION_DURATION = 60; // frames per stage
    private final int TOTAL_SLEEP_STAGES = 3;

    public int commandNum = 0;
    public int keyBindNum = 0; 

    int subState = 0; 

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            Font pixelify = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelifySans-VariableFont_wght.ttf"));
            pixelify40 = pixelify.deriveFont(Font.PLAIN, 40f);
            pixelify26 = pixelify.deriveFont(Font.PLAIN, 26f);
            pixelify22 = pixelify.deriveFont(Font.PLAIN, 22f);
            pixelify36 = pixelify.deriveFont(Font.BOLD, 36f);
            pixelify80 = pixelify.deriveFont(Font.BOLD, 80f);
            pixelify120 = pixelify.deriveFont(Font.BOLD, 120f);
            pixelify15 = pixelify.deriveFont(Font.PLAIN, 15f);
            pixelify50 = pixelify.deriveFont(Font.PLAIN, 50f);
            pixelify30 = pixelify.deriveFont(Font.PLAIN, 30f);
        } catch (FontFormatException | IOException e) {
            pixelify40 = new Font("Arial", Font.PLAIN, 40);
            pixelify26 = new Font("Arial", Font.PLAIN, 26);
            pixelify22 = new Font("Arial", Font.PLAIN, 22);
            pixelify36 = new Font("Arial", Font.BOLD, 36);
            pixelify80 = new Font("Arial", Font.BOLD, 80);
            pixelify120 = new Font("Arial", Font.BOLD, 120);
            pixelify15 = new Font("Arial", Font.PLAIN, 15);
            pixelify50 = new Font("Arial", Font.PLAIN, 50);
            pixelify30 = new Font("Arial", Font.PLAIN, 30);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.menuState) {
            drawMainMenu();
        }

        g2.setFont(pixelify40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            // Draw time in top-right corner
            g2.setFont(pixelify40);
            g2.setColor(Color.WHITE);
            String time = gp.timeM.getTimeString();
            g2.drawString(time, gp.screenWidth - 150, 50);

            // Draw season and day
            String season = gp.timeM.getSeason();
            int day = gp.timeM.getDay();
            String dateText = season + " - Day " + day;
            g2.drawString(dateText, 50, 50);  // Top left corner

            // Draw current map name - Use gp.mapM
            String currentMap = gp.tileM.mapManager.getCurrentMap();
            if (currentMap != null && !currentMap.isEmpty()) {
                // Format the map name properly
                String displayName = currentMap;
                if (currentMap.equals("insideHouse")) {
                    displayName = "Inside House";
                } else if (currentMap.equals("farm")) {
                    displayName = "Farm";
                } else if (currentMap.equals("town")) {
                    displayName = "Town";
                } else if (currentMap.equals("mountainlake")) {
                    displayName = "Mountain Lake";
                } else if (currentMap.equals("forestriver")) {
                    displayName = "Forest River";
                } else if (currentMap.equals("ocean")) {
                    displayName = "Ocean";
                }
                
                String mapText = "Location: " + displayName;
                
                // Draw main white text
                g2.setColor(Color.WHITE);
                g2.drawString(mapText, 50, 100);
                
            }
            drawOnHandPopup();
        }

        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        if (gp.gameState == gp.inventoryState) {
            drawInventory();
        }

        if (gp.gameState == gp.fishingMiniGameState) {
            drawFishingMiniGame();
        }

        if(gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }

        if (gp.gameState == gp.keyBindingState) {
            drawKeyBindings();
        }

        if (gp.gameState == gp.fishingResultState) {
            drawFishingResult();
        }

        if(gp.gameState == gp.shippingBinState){
            drawShippingBin();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(pixelify22);
            g2.setColor(Color.WHITE);
            int init = 360;
            int adder = 20;
            g2.drawString("Player World X: " + gp.player.worldX, 10, init);
            init += adder;
            g2.drawString("Player World Y: " + gp.player.worldY, 10, init);
            init += adder;
            g2.drawString("Col: " + gp.player.worldX / gp.tileSize, 10, init);
            init += adder;
            g2.drawString("Row: " + gp.player.worldY / gp.tileSize, 10, init);
            init += adder;
            g2.drawString("Energy: " + gp.player.getEnergy(), 10, init);  // Add energy display
            init += adder;
            g2.drawString("Gold: " + gp.player.getGold(), 10, init);      // Add gold display
            init += adder;
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            g2.drawString("Interact Pressed: " + interactState, 10, init); // Move down

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

        g2.setFont(pixelify50);
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize ;
        g2.drawString(text, textX, textY);
        
        textX = frameY + 5 * gp.tileSize + 10;
        
        g2.setFont(pixelify30);
        
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
        
        g2.setFont(pixelify40);
        String text = "KEY BINDINGS";
        int textX = getXforCenteredText(text);
        int textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        g2.setFont(pixelify30);
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

    public void drawSleepScreen() {
        // Fill screen with black
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Set text properties
        g2.setFont(pixelify100);
        g2.setColor(Color.WHITE);

        // Build text based on animation stage
        String text = "you fell asleep";
        for (int i = 0; i <= sleepAnimationStage; i++) {
            text += " .";
        }

        // Center the text
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }
    
        public void updateSleepAnimation() {
        if (gp.gameState == gp.sleepState) {
            sleepAnimationTimer++;

            if (sleepAnimationTimer >= SLEEP_ANIMATION_DURATION) {
                sleepAnimationTimer = 0;
                sleepAnimationStage++;

                // When animation is complete, finish sleep
                if (sleepAnimationStage >= TOTAL_SLEEP_STAGES) {
                    finishSleepAnimation();
                }
            }
        }
    }

    // Add method to start sleep animation
    public void startSleepAnimation() {
        sleepAnimationTimer = 0;
        sleepAnimationStage = 0;
        gp.gameState = gp.sleepState;
    }

    // Add method to finish sleep animation
    private void finishSleepAnimation() {
        sleepAnimationTimer = 0;
        sleepAnimationStage = 0;

        // Return to play state and spawn in house
        gp.gameState = gp.playState;
        gp.tileM.mapManager.changeMap("insideHouse", 3, 3);
    }

    public void drawMainMenu() {
        // Background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("res/menu/menuScreen.png")); // Ganti dengan path image kamu
        } catch (IOException e) {
            // Fallback jika image tidak ditemukan
            System.out.println("Background image not found, using solid color");
        }
        
        if (backgroundImage != null) {
            // Draw scaled background image
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            // Fallback: solid color background
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Title with better visibility
        g2.setFont(pixelify80);
        g2.setFont(pixelify120);
        String text = "SPAKBOR HILLS";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Title shadow (darker and more prominent)
        g2.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
        g2.drawString(text, x + 3, y + 3);

        // Title outline (optional, for better readability)
        g2.setColor(new Color(50, 50, 50));
        g2.drawString(text, x + 1, y + 1);
        g2.drawString(text, x - 1, y - 1);
        g2.drawString(text, x + 1, y - 1);
        g2.drawString(text, x - 1, y + 1);

        // Main title text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Menu options with background boxes for better visibility
        g2.setFont(pixelify40);
        
        // START GAME
        text = "START GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        
        if (gp.keyH.menuOption == 0) {
            // Selected option background
            g2.setColor(new Color(255, 255, 0, 100)); // Semi-transparent yellow
            g2.fillRoundRect(x - 20, y - 35, g2.getFontMetrics().stringWidth(text) + 40, 45, 10, 10);
        }
        
        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        // Text shadow
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        // OPTIONS
        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        
        if (gp.keyH.menuOption == 1) {
            g2.setColor(new Color(255, 255, 0, 100));
            g2.fillRoundRect(x - 20, y - 35, g2.getFontMetrics().stringWidth(text) + 40, 45, 10, 10);
        }
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(gp.keyH.menuOption == 1 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        // EXIT
        text = "EXIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        
        if (gp.keyH.menuOption == 2) {
            g2.setColor(new Color(255, 255, 0, 100));
            g2.fillRoundRect(x - 20, y - 35, g2.getFontMetrics().stringWidth(text) + 40, 45, 10, 10);
        }
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(gp.keyH.menuOption == 2 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        // Draw chest icon (if you want to keep it)
        if (chestImage != null) {
            g2.drawImage(chestImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        }
    }
    
    public void drawInventory() {
        int cols = 8;
        int rows = 4;
        int slotSize = 80;
        int slotGapX = 32;
        int slotGapY = 32;

        int invWidth = cols * slotSize + (cols - 1) * slotGapX + 60;
        int invHeight = rows * slotSize + (rows - 1) * (slotGapY + 12) + 100;
        int invX = gp.screenWidth / 2 - invWidth / 2;
        int invY = gp.screenHeight / 2 - invHeight / 2;

        // Kotak utama
        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);

        // Outline putih kotak utama (hanya sekali, sebelum loop)
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(invX, invY, invWidth, invHeight, 30, 30);

        // CLIP: agar item tidak keluar kotak
        Shape oldClip = g2.getClip();
        g2.setClip(invX, invY, invWidth, invHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Inventory";
        int titleX = invX + (invWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, invY + 45);

        int totalGridWidth = cols * slotSize + (cols - 1) * slotGapX;
        int startX = invX + (invWidth - totalGridWidth) / 2;
        int startY = invY + 70;

        List<Entry<items.Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
        entries.sort((a, b) -> {
            boolean aEquip = a.getKey() instanceof items.equipment;
            boolean bEquip = b.getKey() instanceof items.equipment;
            if (aEquip && !bEquip) return -1;
            if (!aEquip && bEquip) return 1;
            return a.getKey().getName().compareToIgnoreCase(b.getKey().getName());
        });

        int maxSlots = cols * rows;

        for (int i = 0; i < entries.size() && i < maxSlots; i++) {
            items.Item item = entries.get(i).getKey();
            int quantity = entries.get(i).getValue();
            int col = i % cols;
            int row = i / cols;
            int x = startX + col * (slotSize + slotGapX);
            int y = startY + row * (slotSize + slotGapY + 12);

            // Slot background
            g2.setColor(new Color(80, 80, 80, 210));
            g2.fillRoundRect(x, y, slotSize, slotSize, 14, 14);

            // Highlight if selected
            if (i == gp.keyH.inventoryCursorIndex) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(x-2, y-2, slotSize+4, slotSize+4, 16, 16);
            }

            // Draw item image
            if (item.getImage() != null) {
                g2.drawImage(item.getImage(), x + 12, y + 12, slotSize - 24, slotSize - 24, null);
            }

            // Draw item name (centered below slot, font lebih besar)
            g2.setColor(Color.WHITE);
            g2.setFont(pixelify15);
            String itemName = item.getName();
            int nameWidth = g2.getFontMetrics().stringWidth(itemName);
            g2.drawString(itemName, x + (slotSize - nameWidth) / 2, y + slotSize + 20);

            // Draw quantity (bottom right of slot)
            String qtyText = "x" + quantity;
            g2.setFont(pixelify22);
            int qtyWidth = g2.getFontMetrics().stringWidth(qtyText);
            g2.drawString(qtyText, x + slotSize - qtyWidth - 6, y + slotSize - 6);
        }

        // Reset clip
        g2.setClip(oldClip);
    }
        
    public void drawFishingMiniGame() {
        int boxWidth = 600;
        int boxHeight = 340;
        int boxX = gp.screenWidth / 2 - boxWidth / 2;
        int boxY = gp.screenHeight / 2 - boxHeight / 2;

        // Background box
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        // Outline putih di sekeliling box
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Mini Game: Fishing!";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, boxY + 60);

        // Instruction
        g2.setFont(pixelify26);
        String instr = "Tebak angka " + gp.fishingMiniGame.getMin() + "-" + gp.fishingMiniGame.getMax() + " untuk dapat ikan!";
        int instrX = getXforCenteredText(instr);
        g2.drawString(instr, instrX, boxY + 110);

        // Sisa tries
        g2.setFont(pixelify22);
        String triesText = "Sisa kesempatan: " + gp.fishingMiniGame.getTries();
        int triesX = getXforCenteredText(triesText);
        g2.drawString(triesText, triesX, boxY + 170);

        // Input box (benar-benar di tengah box)
        g2.setFont(pixelify36);
        String inputText = "Input: ";
        int inputVal = gp.fishingMiniGame.getInput();
        if (inputVal != 0) {
            inputText += inputVal;
        } else {
            inputText += "_";
        }
        int inputBoxWidth = 320;
        int inputBoxHeight = 60;
        int inputBoxX = boxX + (boxWidth - inputBoxWidth) / 2;
        int inputBoxY = boxY + 210;

        // Box input
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(inputBoxX, inputBoxY, inputBoxWidth, inputBoxHeight, 18, 18);
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawRoundRect(inputBoxX, inputBoxY, inputBoxWidth, inputBoxHeight, 18, 18);

        // Text input di tengah box input
        int inputTextWidth = g2.getFontMetrics().stringWidth(inputText);
        int inputTextX = inputBoxX + (inputBoxWidth - inputTextWidth) / 2;
        int inputTextY = inputBoxY + inputBoxHeight / 2 + g2.getFontMetrics().getAscent() / 2 - 6;
        g2.drawString(inputText, inputTextX, inputTextY);
    }

    public void drawOnHandPopup() {
        items.Item onhand = gp.player.getOnhandItem();
        if (onhand == null) return;

        int popupWidth = 240;
        int popupHeight = 54;
        int marginX = 18;
        int marginY = 18;
        int x = gp.getWidth() - popupWidth - marginX;
        int y = gp.getHeight() - popupHeight - marginY;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(x + 4, y + 4, popupWidth, popupHeight, 18, 18);

        // Background (transparan, glossy)
        g2.setColor(new Color(40, 40, 60, 200));
        g2.fillRoundRect(x, y, popupWidth, popupHeight, 18, 18);

        // Glossy effect
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fillRoundRect(x + 2, y + 2, popupWidth - 4, popupHeight / 2, 16, 12);

        // Outline
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawRoundRect(x, y, popupWidth, popupHeight, 18, 18);

        // Gambar item (bulat)
        int iconX = x + 10;
        int iconY = y + 6;
        int iconSize = 40;
        g2.setColor(new Color(255,255,255,60));
        g2.fillOval(iconX-2, iconY-2, iconSize+4, iconSize+4);
        if (onhand.getImage() != null) {
            g2.setClip(iconX, iconY, iconSize, iconSize);
            g2.drawImage(onhand.getImage(), iconX, iconY, iconSize, iconSize, null);
            g2.setClip(null);
        }

        // Nama item (font lebih kecil, bold, sedikit shadow)
        g2.setFont(pixelify15);
        String name = onhand.getName();
        int nameX = iconX + iconSize + 8;
        int nameY = y + popupHeight / 2 + 6;
        g2.setColor(new Color(0,0,0,120));
        g2.drawString(name, nameX+1, nameY+1);
        g2.setColor(Color.WHITE);
        g2.drawString(name, nameX, nameY);
    }
    
    public void drawFishingResult() {
        String text = gp.fishingMiniGame.getResultMessage();
        items.fish item = gp.fishingMiniGame.getResultItem();

        int boxWidth = 500;
        int boxHeight = 220;
        int boxX = gp.getWidth() / 2 - boxWidth / 2;
        int boxY = gp.getHeight() / 2 - boxHeight / 2;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(boxX + 6, boxY + 6, boxWidth, boxHeight, 36, 36);

        // Background
        g2.setColor(new Color(40, 70, 40, 230));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        // Outline putih
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        // Pesan utama (wrap jika terlalu panjang)
        g2.setFont(pixelify30);
        g2.setColor(Color.WHITE);

        // Word wrap manual
        java.util.List<String> lines = new java.util.ArrayList<>();
        int maxTextWidth = boxWidth - 40;
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            int testWidth = g2.getFontMetrics().stringWidth(testLine);
            if (testWidth > maxTextWidth && line.length() > 0) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }
        if (line.length() > 0) lines.add(line.toString());

        int totalTextHeight = lines.size() * g2.getFontMetrics().getHeight();
        int startY = boxY + 60 + (item != null ? 0 : 30) - totalTextHeight / 2 + 10;
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            int textWidth = g2.getFontMetrics().stringWidth(l);
            int textX = boxX + (boxWidth - textWidth) / 2;
            int textY = startY + i * g2.getFontMetrics().getHeight();
            g2.drawString(l, textX, textY);
        }

        // Jika berhasil, tampilkan gambar ikan di tengah bawah
        if (item != null && item.getImage() != null) {
            int imgW = 64, imgH = 64;
            int imgX = boxX + (boxWidth - imgW) / 2;
            int imgY = boxY + 110;
            g2.drawImage(item.getImage(), imgX, imgY, imgW, imgH, null);
        }

        // Instruksi keluar (kecil, di bawah, tengah)
        g2.setFont(pixelify22);
        String instr = "Tekan ENTER untuk menutup";
        int instrWidth = g2.getFontMetrics().stringWidth(instr);
        int instrX = boxX + (boxWidth - instrWidth) / 2;
        int instrY = boxY + boxHeight - 28;
        g2.setColor(new Color(255,255,255,180));
        g2.drawString(instr, instrX, instrY);
    }

    public void drawShippingBin(){
        int cols = 8;
        int rows = 4;
        int slotSize = 60;
        int slotGapX = 48;
        int slotGapY = 32;

        int invWidth = cols * slotSize + (cols - 1) * slotGapX + 120;
        int invHeight = rows * slotSize + (rows - 1) * (slotGapY + 12) - 40;
        int invX = gp.screenWidth / 2 - invWidth / 2;
        int invY = gp.screenHeight / 2 - invHeight / 2 + 120;

        int binWidth = 8 * slotSize + (1) * slotGapX;
        int binHeight = rows * slotSize + (rows - 1) * (slotGapY + 12) - 120;
        int binX = gp.screenWidth/2 - binWidth / 2;
        int binY = gp.screenHeight/2 - binHeight / 2 - 200;

        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.fillRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.drawRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Inventory";
        int titleX = invX + (invWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, invY + 45);

        String bintitle = "Shipping Bin";
        int bintitleX = binX + (binWidth - g2.getFontMetrics().stringWidth(bintitle)) / 2; // Fix: use bintitle, not title
        g2.drawString(bintitle, bintitleX, binY + 45);

        g2.setColor(new Color(80, 80, 80, 210));
        int previewSlotX = binX + binWidth / 2 - slotSize - 160;
        int previewSlotY = binY + binHeight / 2 - slotSize + 20;
        g2.fillRoundRect(previewSlotX, previewSlotY, slotSize * 2, slotSize * 2, 14, 14);

        int totalGridWidth = cols * slotSize + (cols - 1) * slotGapX;
        int startX = invX + (invWidth - totalGridWidth) / 2; 
        int startY = invY + 70; 

        List<Entry<items.Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
        entries.sort((a, b) -> {
            boolean aEquip = a.getKey() instanceof items.equipment;
            boolean bEquip = b.getKey() instanceof items.equipment;
            if (aEquip && !bEquip) return -1;
            if (!aEquip && bEquip) return 1;
            return a.getKey().getName().compareToIgnoreCase(b.getKey().getName());
        });

        int maxSlots = cols * rows;

        for (int i = 0; i < entries.size() && i < maxSlots; i++) {
            items.Item item = entries.get(i).getKey();
            int quantity = entries.get(i).getValue();
            int col = i % cols;
            int row = i / cols;
            int x = startX + col * (slotSize + slotGapX);
            int y = startY + row * (slotSize + slotGapY + 12);

            // Slot background
            g2.setColor(new Color(80, 80, 80, 210));
            g2.fillRoundRect(x, y, slotSize, slotSize, 14, 14);

            // Highlight if selected
            if (i == gp.keyH.inventoryCursorIndex) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(x-2, y-2, slotSize+4, slotSize+4, 16, 16);
            }

            if (item.getImage() != null) {
                g2.drawImage(item.getImage(), x + 12, y + 12, slotSize - 24, slotSize - 24, null);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(pixelify15);
            String itemName = item.getName();
            int nameWidth = g2.getFontMetrics().stringWidth(itemName);
            g2.drawString(itemName, x + (slotSize - nameWidth) / 2, y + slotSize + 20);

            String qtyText = "x" + quantity;
            g2.setFont(pixelify22);
            int qtyWidth = g2.getFontMetrics().stringWidth(qtyText);
            g2.drawString(qtyText, x + slotSize - qtyWidth - 6, y + slotSize - 6);
        }
        
        items.Item selected = gp.player.getOnhandItem();



        if(gp.player.getOnhandItem() == null) {
            g2.setColor(Color.WHITE);
            g2.setFont(pixelify26);

            String binitem = "Select an Item";
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; 
            g2.drawString(binitem, binitemX, binY + 90 + 10);
        }

        else if (gp.player.getOnhandItem() != null && gp.player.getOnhandItem() instanceof items.buysellable ) {   
            g2.setFont(pixelify22);
            String binitem = "nama : " + gp.player.getOnhandItem().getName();
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; 
            g2.drawString(binitem, binitemX, binY + 90 + 10);

            String binprice = "harga : " + ((items.buysellable)gp.player.getOnhandItem()).getHargaJual();
            int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
            g2.drawString(binprice, binpriceX, binY + 135 + 10);

            String binoption = "apakah anda yakin";
            int binoptionX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
            g2.drawString(binoption, binoptionX, binY + 180 + 10);

            String binoption2 = "ingin menjual item ini? (Y/N)";
            int binoption2X = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
            g2.drawString(binoption2, binoption2X, binY + 210 + 10);
        }

        else if (gp.player.getOnhandItem() != null && !(gp.player.getOnhandItem() instanceof items.buysellable) ) {  
            g2.setFont(pixelify22);
            String binitem = "nama : " + gp.player.getOnhandItem().getName();
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; 
            g2.drawString(binitem, binitemX, binY + 90 + 10);

            String binprice = "item ini tidak bisa dijual!";
            int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
            g2.drawString(binprice, binpriceX, binY + 135 + 10);

        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}