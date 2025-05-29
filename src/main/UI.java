package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; // Tambahkan ini

import object.OBJ_Chest;
import object.OBJ_Door;

import java.util.ArrayList;
import java.util.List;

public class UI {

    private Font pixelify40;
    private Font pixelify26;
    private Font pixelify22;
    private Font pixelify36;
    private Font pixelify80;
    private Font pixelify15;
    private Font pixelify13;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;

    // Add sleep animation variables
    private int sleepAnimationTimer = 0;
    private int sleepAnimationStage = 0;
    private final int SLEEP_ANIMATION_DURATION = 60; // frames per stage
    private final int TOTAL_SLEEP_STAGES = 3;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            Font pixelify = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelifySans-VariableFont_wght.ttf"));
            pixelify40 = pixelify.deriveFont(Font.PLAIN, 40f);
            pixelify26 = pixelify.deriveFont(Font.PLAIN, 26f);
            pixelify22 = pixelify.deriveFont(Font.PLAIN, 22f);
            pixelify36 = pixelify.deriveFont(Font.BOLD, 36f);
            pixelify80 = pixelify.deriveFont(Font.BOLD, 80f);
            pixelify15 = pixelify.deriveFont(Font.PLAIN, 15f);
            pixelify13 = pixelify.deriveFont(Font.PLAIN, 13f);
        } catch (FontFormatException | IOException e) {
            pixelify40 = new Font("Arial", Font.PLAIN, 40);
            pixelify26 = new Font("Arial", Font.PLAIN, 26);
            pixelify22 = new Font("Arial", Font.PLAIN, 22);
            pixelify36 = new Font("Arial", Font.BOLD, 36);
            pixelify80 = new Font("Arial", Font.BOLD, 80);  
            pixelify15 = new Font("Arial", Font.PLAIN, 15);
            pixelify13 = new Font("Arial", Font.PLAIN, 13);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(gp.gameState == gp.menuState) {
            drawMainMenu();
        }

        g2.setFont(pixelify40);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.playState) {
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
        }

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if(gp.gameState == gp.inventoryState) {
            drawInventory();
        }

        if(gp.gameState == gp.fishingMiniGameState) {
            drawFishingMiniGame();
        }
        
        // Add sleep screen drawing
        if(gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }

        if(gp.gameState == gp.shippingBinState){
            drawShippingBin();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(pixelify22);
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
        g2.setFont(pixelify80);
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
        g2.setFont(pixelify40);
        
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
        y += gp.tileSize * 4;
        
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
        g2.setColor(new Color(30, 30, 60, 230));
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
        g2.setColor(new Color(60, 60, 100, 180));
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
            
    public void drawSleepScreen() {
        // Fill screen with black
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // Set text properties
        g2.setFont(pixelify80);
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
    
    // Add method to update sleep animation
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
    
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    public void drawShippingBin(){
        int cols = 8;
        int rows = 4;
        int slotSize = 60;
        int slotGapX = 48;
        int slotGapY = 32;

        int invWidth = cols * slotSize + (cols - 1) * slotGapX + 120;
        int invHeight = rows * slotSize + (rows - 1) * (slotGapY + 12) ; // Add padding for title
        int invX = gp.screenWidth / 2 - invWidth / 2;
        int invY = gp.screenHeight / 2 - invHeight / 2 + 180;

        int binWidth = 8 * slotSize + (1) * slotGapX;
        int binHeight = rows * slotSize + (rows - 1) * (slotGapY + 12) - 120;
        int binX = gp.screenWidth/2 - binWidth / 2;
        int binY = gp.screenHeight/2 - binHeight / 2 - 200;

        // Draw main boxes
        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.fillRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        // Draw outlines
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.drawRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        // Draw inventory title
        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Inventory";
        int titleX = invX + (invWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, invY + 45);

        // Draw bin title
        String bintitle = "Shipping Bin";
        int bintitleX = binX + (binWidth - g2.getFontMetrics().stringWidth(bintitle)) / 2; // Fix: use bintitle, not title
        g2.drawString(bintitle, bintitleX, binY + 45);

        // Draw bin preview slot
        g2.setColor(new Color(80, 80, 80, 210));
        int previewSlotX = binX + binWidth / 2 - slotSize - 160;
        int previewSlotY = binY + binHeight / 2 - slotSize + 20;
        g2.fillRoundRect(previewSlotX, previewSlotY, slotSize * 2, slotSize * 2, 14, 14);



        // INVENTORY GRID - Calculate proper positions
        int totalGridWidth = cols * slotSize + (cols - 1) * slotGapX;
        int startX = invX + (invWidth - totalGridWidth) / 2; // Center grid in inventory box
        int startY = invY + 70; // Start below title

        // Get and sort inventory items
        List<Entry<items.Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
        entries.sort((a, b) -> {
            boolean aEquip = a.getKey() instanceof items.equipment;
            boolean bEquip = b.getKey() instanceof items.equipment;
            if (aEquip && !bEquip) return -1;
            if (!aEquip && bEquip) return 1;
            return a.getKey().getName().compareToIgnoreCase(b.getKey().getName());
        });

        int maxSlots = cols * rows;

        // Draw inventory items
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

            // Draw item name (centered below slot)
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
        items.Item selected = gp.player.getOnhandItem();



        if(gp.player.getOnhandItem() == null){
        // Draw bin info text
        g2.setColor(Color.WHITE);
        g2.setFont(pixelify26);
        
        String binitem = "Pilih item!";
        int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; // Fix: use actual text width
        g2.drawString(binitem, binitemX, binY + 90 + 10);
        }
        else if (gp.player.getOnhandItem() != null && gp.player.getOnhandItem() instanceof items.buysellable ){  
        g2.setFont(pixelify22);
        String binitem = "Nama : " + gp.player.getOnhandItem().getName();
        int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; // Fix: use actual text width
        g2.drawString(binitem, binitemX, binY + 90 + 10);

        String binprice = "Harga : " + ((items.buysellable)gp.player.getOnhandItem()).getHargaJual();
        int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
        g2.drawString(binprice, binpriceX, binY + 135 + 10);

        String binoption = "Apakah anda yakin";
        int binoptionX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
        g2.drawString(binoption, binoptionX, binY + 180 + 10);

        String binoption2 = "Ingin menjual item ini? (Y)";
        int binoption2X = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
        g2.drawString(binoption2, binoption2X, binY + 210 + 10);
        }

        else if (gp.player.getOnhandItem() != null && !(gp.player.getOnhandItem() instanceof items.buysellable) ){  
        g2.setFont(pixelify22);
        String binitem = "Nama : " + gp.player.getOnhandItem().getName();
        int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ; // Fix: use actual text width
        g2.drawString(binitem, binitemX, binY + 90 + 10);

        String binprice = "Item ini tidak bisa dijual!";
        int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
        g2.drawString(binprice, binpriceX, binY + 135 + 10);

        }
        }


        
    }
