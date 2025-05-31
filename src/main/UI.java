package main;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.awt.FontFormatException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
import object.OBJ_Door;
import object.OBJ_Oven; 

import java.util.ArrayList;
import java.util.List;
import items.*;

public class UI {

    private Font pixelify40;
    private Font pixelify26;
    private Font pixelify22;
    private Font pixelify36;
    private Font pixelify80;
    private Font pixelify120;
    private Font pixelify15;
    private Font pixelify16;
    private Font pixelify50;
    private Font pixelify70;
    private Font pixelify32;
    private Font pixelify18;
    private Font pixelify12;
    private Font pixelify30;
    private Font pixelify100;
    private final Color SELECTED_COLOR = new Color(255, 255, 0);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255);

    BufferedImage chestImage;

    GamePanel gp;
    Graphics2D g2;

    private int sleepAnimationTimer = 0;
    private int sleepAnimationStage = 0;
    private final int SLEEP_ANIMATION_DURATION = 60;
    private final int TOTAL_SLEEP_STAGES = 3;

    public int commandNum = 0;
    public int keyBindNum = 0;
    public int setupGameInfoNum = 0;
    public boolean isMale = false;

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
            pixelify50 = pixelify.deriveFont(Font.PLAIN, 50f);
            pixelify70 = pixelify.deriveFont(Font.PLAIN, 70f);
            pixelify32 = pixelify.deriveFont(Font.PLAIN, 32f);
            pixelify18 = pixelify.deriveFont(Font.PLAIN, 18f);
            pixelify16 = pixelify.deriveFont(Font.PLAIN, 16f);
            pixelify80 = pixelify.deriveFont(Font.BOLD, 80f);
            pixelify30 = pixelify.deriveFont(Font.PLAIN, 30f);
            pixelify15 = pixelify.deriveFont(Font.PLAIN, 15f);
        } catch (FontFormatException | IOException e) {
            pixelify40 = new Font("Arial", Font.PLAIN, 40);
            pixelify26 = new Font("Arial", Font.PLAIN, 26);
            pixelify22 = new Font("Arial", Font.PLAIN, 22);
            pixelify36 = new Font("Arial", Font.BOLD, 36);
            pixelify80 = new Font("Arial", Font.BOLD, 80);
            pixelify120 = new Font("Arial", Font.BOLD, 120);
            pixelify15 = new Font("Arial", Font.PLAIN, 15);
            pixelify16 = new Font("Arial", Font.PLAIN, 16);
            pixelify50 = new Font("Arial", Font.PLAIN, 50);
            pixelify70 = new Font("Arial", Font.PLAIN, 70);
            pixelify32 = new Font("Arial", Font.PLAIN, 32);
            pixelify18 = new Font("Arial", Font.PLAIN, 18);
            pixelify12 = new Font("Arial", Font.PLAIN, 12);
            pixelify30 = new Font("Arial", Font.PLAIN, 30);
        }
    }


    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(gp.gameState == gp.menuState) {
            drawMainMenu();
        }

        g2.setFont(pixelify40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            drawTopRightInfoPanel();
            drawOnHandPopup();
        }

        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        if(gp.gameState == gp.inventoryState) {
            drawInventory();
        }

        if (gp.gameState == gp.npcInteractionState) {
            drawInteractionMenu();
        }

        if (gp.gameState == gp.npcChatState) {
            drawChatNPC();
        }
        if (gp.gameState == gp.npcInfoState) {
            drawNPCInfo();
        }

        if (gp.gameState == gp.endGameStatsState) {
            drawEndGameStats();
            return;
        }

        if (gp.gameState == gp.npcGivingGiftState) {
            drawNPCGift();
        }

        if( gp.gameState == gp.npcProposalState) {
            drawNPCProposal();
        }

        if (gp.gameState == gp.npcMarryState) {
            drawNPCMarry();
        }

        if(gp.gameState == gp.fishingMiniGameState) {
            drawFishingMiniGame();
        }

        if (gp.gameState == gp.cookingState) {
            drawCookingUI();
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

        if (gp.gameState == gp.storeState) {
            drawStore();
        }

        if (gp.gameState == gp.setupGameInfoState) {
            drawSetupGameInfo();
        }

        if (gp.keyH.showDebug) {
            g2.setFont(pixelify22);
            g2.setColor(Color.WHITE);
            int init = 360;
            int adder = 20;
            g2.drawString("Player Name: " + gp.setupGame.getName(), 10, init);
            init += adder;
            g2.drawString("Player Farm Name: " + gp.setupGame.getFarmName(), 10, init);
            init += adder;
            g2.drawString("Player World X: " + gp.player.worldX, 10, init);
            init += adder;
            g2.drawString("Player World Y: " + gp.player.worldY, 10, init);
            init += adder;
            g2.drawString("Col: " + gp.player.worldX / gp.tileSize, 10, init);
            init += adder;
            g2.drawString("Row: " + gp.player.worldY / gp.tileSize, 10, init);
            init += adder;
            g2.drawString("Energy: " + gp.player.getEnergy(), 10, init);  
            init += adder;
            g2.drawString("Gold: " + gp.player.getGold(), 10, init);      
            init += adder;
            String interactState = gp.keyH.interactPressed ? "true" : "false";
            g2.drawString("Interact Pressed: " + interactState, 10, 520); 
            
            String selectedItem = "Selected Item: None";
            if (gp.player.getOnhandItem() != null) {
                selectedItem = "Selected Item: " + gp.player.getOnhandItem().getName();
            }
            g2.drawString(selectedItem, 10, 540); 
            
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] != null && gp.obj[i] instanceof OBJ_Door) {
                    OBJ_Door door = (OBJ_Door) gp.obj[i];
                    g2.drawString(door.getMessage(), 10, gp.screenHeight - 50);
                }
            }
        }
    }

    private void drawSubWindow(int frameX, int frameY, int frameWidth, int frameHeight) {
        
        Color backgroundColor = new Color(139, 69, 19, 240); 
        g2.setColor(backgroundColor);
        g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);
        
        Color borderColor = new Color(205, 133, 63, 255);
        g2.setColor(borderColor);
        g2.setStroke(new java.awt.BasicStroke(5));
        g2.drawRoundRect(frameX + 5, frameY + 5, frameWidth - 10, frameHeight - 10, 25, 25);
    }

    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height, float opacity) {
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        Color backgroundColor = new Color(244, 200, 125);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, width, height, 35, 35); 


        Color borderColor = new Color(134, 52, 19);
        g2.setColor(borderColor);
        g2.setStroke(new java.awt.BasicStroke(10));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

        g2.setComposite(originalComposite);
    }

    public void drawOptionsScreen() {
        int frameWidth = gp.tileSize * 7;
        int frameHeight = gp.tileSize * 7;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 ;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

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

        g2.setFont(pixelify70);
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize + 20 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);
        
        textX = frameX +  gp.tileSize -24 ;
        
        g2.setFont(pixelify32);

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
                    gp.playMusic(0);
                } else {
                    gp.stopMusic();
                }
            }
        }

        textY += gp.tileSize *6/10 ;
        g2.drawString("Key Bindings", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX -18, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.keyBindingState;
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
        
        
        textX = frameY + 7 * gp.tileSize ;
        textY = frameY + gp.tileSize * 15/8 + 20;

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
        int frameWidth = gp.tileSize * 7;
        int frameHeight = gp.tileSize * 7;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 ; 
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify50);
        String text = "KEY BINDINGS";
        int textX = getXforCenteredText(text);
        int textY = frameY + gp.tileSize + 20;
        g2.drawString(text, textX, textY);
        
        g2.setFont(pixelify32);
        textX = frameX +  gp.tileSize - 25;
        
        textY += gp.tileSize * 7 / 8;
        g2.drawString("Move Up", textX, textY);
  
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Down", textX, textY);
   
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Left", textX, textY);
  
        textY += gp.tileSize * 6 / 10;
        g2.drawString("Move Right", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("Run", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("Inventory", textX, textY);

        textY += gp.tileSize * 6 / 10;
        g2.drawString("Interact", textX, textY);     
        
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

        
        textX = frameX +  5 * gp.tileSize + 10;
        textY = frameY + gp.tileSize + 20 + gp.tileSize * 7 / 8;
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

        
    }

    public void drawSleepScreen() {
        
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        
        g2.setFont(pixelify100);
        g2.setColor(Color.WHITE);

        
        String text = "you fell asleep";
        for (int i = 0; i <= sleepAnimationStage; i++) {
            text += " .";
        }

        
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

                
                if (sleepAnimationStage >= TOTAL_SLEEP_STAGES) {
                    finishSleepAnimation();
                }
            }
        }
    }

    
    public void startSleepAnimation() {
        sleepAnimationTimer = 0;
        sleepAnimationStage = 0;
        gp.gameState = gp.sleepState;
    }

    
    private void finishSleepAnimation() {
        sleepAnimationTimer = 0;
        sleepAnimationStage = 0;

        
        gp.gameState = gp.playState;
        gp.tileM.mapManager.changeMap("insideHouse", 3, 3);
    }

    public void drawMainMenu() {
        
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("res/menu/menuScreen.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (backgroundImage != null) {
            
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        
        g2.setFont(pixelify80);
        g2.setFont(pixelify120);
        String text = "SPAKBOR HILLS";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 5;

        
        g2.setColor(new Color(0, 0, 0, 180)); 
        g2.drawString(text, x + 3, y + 3);

        
        g2.setColor(new Color(50, 50, 50));
        g2.drawString(text, x + 1, y + 1);
        g2.drawString(text, x - 1, y - 1);
        g2.drawString(text, x + 1, y - 1);
        g2.drawString(text, x - 1, y + 1);

        
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        
        g2.setFont(pixelify40);
        
        
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        
        if (gp.keyH.menuOption == 0) {
            
            g2.setColor(new Color(255, 255, 0, 100)); 
            g2.fillRoundRect(x - 20, y - 35, g2.getFontMetrics().stringWidth(text) + 40, 45, 10, 10);
        }

        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(gp.keyH.menuOption == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        g2.drawString(text, x, y);

        
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

        
        if (chestImage != null) {
            g2.drawImage(chestImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        }
    }

    public void drawInteractionMenu() {
        BufferedImage icon = null;
        BufferedImage talk_selected = null, talk_def = null;
        BufferedImage gift_selected = null, gift_def = null;
        BufferedImage info_selected = null, info_def = null;
        BufferedImage marry_selected = null, marry_def = null;
        BufferedImage proposal_selected = null, proposal_def = null;

        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return; 

        String name = npc.name;

        try {
            
            icon = ImageIO.read(new File("res/ui/" + name.toLowerCase() + "_icon.png"));

            
            talk_selected = ImageIO.read(new File("res/ui/talk_selected.png"));
            talk_def = ImageIO.read(new File("res/ui/talk_def.png"));
            gift_selected = ImageIO.read(new File("res/ui/gift_selected.png"));
            gift_def = ImageIO.read(new File("res/ui/gift_def.png"));
            info_selected = ImageIO.read(new File("res/ui/info_selected.png"));
            info_def = ImageIO.read(new File("res/ui/info_def.png"));
            marry_selected = ImageIO.read(new File("res/ui/marry_selected.png"));
            marry_def = ImageIO.read(new File("res/ui/marry_def.png"));
            proposal_selected = ImageIO.read(new File("res/ui/propose_selected.png"));
            proposal_def = ImageIO.read(new File("res/ui/propose_def.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        int gap = 5;

        if (icon != null && talk_selected != null) {
            int maxWidth = talk_selected.getWidth(); 
            int itemHeight = talk_selected.getHeight();
            int totalHeight = itemHeight * 5 + gap * 4;

            int x = gp.screenWidth - maxWidth - margin;
            int y = (gp.screenHeight - totalHeight) / 2;

            
            int leftImageX = x - icon.getWidth() - gap;
            int leftImageY = y + (totalHeight - icon.getHeight()) / 2;
            g2.drawImage(icon, leftImageX, leftImageY, null);

            
            BufferedImage[] options = {
                gp.keyH.menuOption == 0 ? talk_selected : talk_def,
                gp.keyH.menuOption == 1 ? gift_selected : gift_def,
                gp.keyH.menuOption == 2 ? info_selected : info_def,
                gp.keyH.menuOption == 3 ? marry_selected : marry_def,
                gp.keyH.menuOption == 4 ? proposal_selected : proposal_def
            };

            
            for (BufferedImage img : options) {
                g2.drawImage(img, x, y, null);
                y += itemHeight + gap;
            }

            
            g2.setFont(pixelify18);
            g2.setColor(Color.RED);
            g2.drawString("menuOption: " + gp.keyH.menuOption, 20, 30);
        }
    }

    public void drawNPCInfo() {
        
        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return;

        String name = npc.name.toLowerCase();
        BufferedImage infoImg = null;
        try {
            infoImg = ImageIO.read(new File("res/ui/" + name + "_template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        if (infoImg != null) {
            int infoWidth = infoImg.getWidth();
            int infoHeight = infoImg.getHeight();
            int x = (gp.screenWidth - infoWidth) / 2;
            int y = gp.screenHeight - infoHeight - margin;
            g2.drawImage(infoImg, x, y, null);

            
            g2.setFont(pixelify15);
            g2.setColor(new Color(139, 69, 19)); 

            int textAreaWidth = infoWidth - 200; 
            int textX = x + 200; 
            int textY = y + 25;

            
            g2.setColor(new Color(205, 133, 63));
            g2.drawString("Loved Items:", textX, textY);
            g2.drawString("Heart Point: " + npc.getHeartPoints(), textX + 317, textY);
            textY += g2.getFontMetrics().getHeight();
            g2.setColor(new Color(139, 69, 19));
            textY = drawWrappedList(g2, npc.getLovedItems(), textX, textY, textAreaWidth);

            
            g2.setColor(new Color(205, 133, 63));
            g2.drawString("Liked Items:", textX, textY);
            textY += g2.getFontMetrics().getHeight();
            g2.setColor(new Color(139, 69, 19));
            textY = drawWrappedList(g2, npc.getLikedItems(), textX, textY, textAreaWidth);

            
            g2.setColor(new Color(205, 133, 63));
            g2.drawString("Hated Items:", textX, textY);
            textY += g2.getFontMetrics().getHeight();
            g2.setColor(new Color(139, 69, 19));
            textY = drawWrappedList(g2, npc.getHatedItems(), textX, textY, textAreaWidth);
        }
    }

    public void drawNPCGift() {
        
        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return;

        String name = npc.name.toLowerCase();
        BufferedImage infoImg = null;
        try {
            infoImg = ImageIO.read(new File("res/ui/" + name + "_template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        if (infoImg != null) {
            int infoWidth = infoImg.getWidth();
            int infoHeight = infoImg.getHeight();
            int x = (gp.screenWidth - infoWidth) / 2;
            int y = gp.screenHeight - infoHeight - margin;
            g2.drawImage(infoImg, x, y, null);

            items.Item onhand = gp.player.getOnhandItem();
            String message;
            String effect = "";

            if (onhand == null) {
                message = "Kamu tidak memegang item apapun.";
            } else if (onhand instanceof items.equipment) {
                message = "Kamu tidak bisa memberikan equipment kepada NPC!";
            } else {
                String itemName = onhand.getName();
                if (npc.getLovedItems().contains(itemName)) {
                    message = "Kamu memberikan " + itemName + " kepada " + npc.name ;
                    effect = "Itu adalah loved item! (+25 HP)";
                } else if (npc.getLikedItems().contains(itemName)) {
                    message = "Kamu memberikan " + itemName + " kepada " + npc.name ;
                    effect = "Itu adalah liked item! (+20 HP)";
                } else if (
                    (npc.getHatedItems().isEmpty() && !npc.getLovedItems().contains(itemName) && !npc.getLikedItems().contains(itemName))
                    || npc.getHatedItems().contains(itemName)
                    || (npc.getHatedItems().contains("Fish") && onhand instanceof items.fish)
                ) {
                    message = "Kamu memberikan " + itemName + " kepada " + npc.name ;
                    effect = "Itu adalah hated item! (-25 HP)";
                } else {
                    message = "Kamu memberikan " + itemName + " kepada " + npc.name ;
                    effect = "Tidak berefek.";
                }
            }

            int textX = x + 195;
            int textY = y + 25;

            g2.setFont(pixelify15);
            g2.setColor(new Color(139, 69, 19));
            String[] lines = {message, effect};
            for (String line : lines) {
                g2.drawString(line, textX, textY);
                textY += g2.getFontMetrics().getHeight() + 8;
            }
    
        }
    }

    public void drawNPCProposal() {
        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return;

        String name = npc.name.toLowerCase();
        BufferedImage infoImg = null;
        try {
            infoImg = ImageIO.read(new File("res/ui/" + name + "_template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        if (infoImg != null) {
            int infoWidth = infoImg.getWidth();
            int infoHeight = infoImg.getHeight();
            int x = (gp.screenWidth - infoWidth) / 2;
            int y = gp.screenHeight - infoHeight - margin;
            g2.drawImage(infoImg, x, y, null);

            g2.setFont(pixelify15);
            g2.setColor(new Color(139, 69, 19));
            String message;
            if (gp.player.inventoryManager.findItemByName("Proposal Ring") == null) {
                message = "Kamu tidak punya Proposal Ring!";
            } else if (npc.getHeartPoints() < 150) {
                message = "Heart Points " + npc.name + " belum maksimal!";
            } else if (gp.player.fianceToWho != null) {
                message = gp.player.fianceToWho + " sudah menjadi tunanganmu!";
            } else {
                message = "Tekan ENTER untuk melamar " + npc.name + ".";
            }
            g2.drawString(message, x + 200, y + 40);

        }
    }

    public void drawNPCMarry() {
        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return;

        String name = npc.name.toLowerCase();
        BufferedImage infoImg = null;
        try {
            infoImg = ImageIO.read(new File("res/ui/" + name + "_template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        if (infoImg != null) {
            int infoWidth = infoImg.getWidth();
            int infoHeight = infoImg.getHeight();
            int x = (gp.screenWidth - infoWidth) / 2;
            int y = gp.screenHeight - infoHeight - margin;
            g2.drawImage(infoImg, x, y, null);

            g2.setFont(pixelify15);
            g2.setColor(new Color(139, 69, 19));
            String message;
            if (gp.player.inventoryManager.findItemByName("Proposal Ring") == null) {
                message = "Kamu tidak punya Proposal Ring!";
            } else if (gp.player.fianceToWho != npc.name) {
                message = npc.name + " belum menjadi tunanganmu!";
            } else if (!npc.canMarryToday()) {
                message = "Tunggu satu hari lagi untuk menikahinya.";
            } else {
                message = "Tekan ENTER untuk menikahi " + npc.name + ".";
            }
            g2.drawString(message, x + 200, y + 40);


        }
    }

    private int drawWrappedList(Graphics2D g2, List<String> items, int x, int y, int maxWidth) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            String next = (line.length() == 0 ? "" : ", ") + item;
            int width = g2.getFontMetrics().stringWidth(line + next);
            if (width > maxWidth && line.length() > 0) {
                g2.drawString(line.toString(), x, y);
                y += g2.getFontMetrics().getHeight();
                line = new StringBuilder(item);
            } else {
                if (line.length() > 0) line.append(", ");
                line.append(item);
            }
        }
        if (line.length() > 0) {
            g2.drawString(line.toString(), x, y);
            y += g2.getFontMetrics().getHeight();
        }
        return y;
    }

    public void drawChatNPC() {
        
        object.OBJ_NPC npc = null;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof object.OBJ_NPC && gp.obj[i] != null) {
                int dx = Math.abs(gp.player.worldX - gp.obj[i].worldX) / gp.tileSize;
                int dy = Math.abs(gp.player.worldY - gp.obj[i].worldY) / gp.tileSize;
                if (dx + dy <= 1) {
                    npc = (object.OBJ_NPC) gp.obj[i];
                    break;
                }
            }
        }
        if (npc == null) return;

        String name = npc.name.toLowerCase();
        BufferedImage chatImg = null;
        try {
            chatImg = ImageIO.read(new File("res/ui/" + name + "_chat.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int margin = 50;
        if (chatImg != null) {
            int chatWidth = chatImg.getWidth();
            int chatHeight = chatImg.getHeight();
            int x = (gp.screenWidth - chatWidth) / 2;
            int y = gp.screenHeight - chatHeight - margin;

            
            g2.drawImage(chatImg, x, y, null);
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

    
        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);

        
        g2.setColor(new Color(205, 133, 63, 255)); 
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(invX, invY, invWidth, invHeight, 30, 30);

        
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

            
            g2.setColor(new Color(90, 58, 42, 200)); 
            g2.fillRoundRect(x, y, slotSize, slotSize, 14, 14);

            
            if (i == gp.keyH.cursorIndex) {
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
            if (itemName.length() > 8) {
                itemName = itemName.substring(0, 8) + "..";
            }
            int nameWidth = g2.getFontMetrics().stringWidth(itemName);
            g2.drawString(itemName, x + (slotSize - nameWidth) / 2, y + slotSize + 20);

            
            String qtyText = "x" + quantity;
            g2.setFont(pixelify22);
            int qtyWidth = g2.getFontMetrics().stringWidth(qtyText);
            g2.drawString(qtyText, x + slotSize - qtyWidth - 6, y + slotSize - 6);
        }

        
        g2.setClip(oldClip);
    }
  
    public void drawFishingMiniGame() {
        int boxWidth = 600;
        int boxHeight = 340;
        int boxX = (gp.screenWidth - boxWidth) / 2;
        int boxY = (gp.screenHeight - boxHeight) / 2 ;

        g2.setColor(new Color(139, 69, 19, 240));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        g2.setColor(new Color(205, 133, 63, 255));
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Mini Game: Fishing!";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, boxY + 60);

        g2.setFont(pixelify26);
        String instr = "Tebak angka " + gp.fishingMiniGame.getMin() + "-" + gp.fishingMiniGame.getMax() + " untuk dapat ikan!";
        int instrX = getXforCenteredText(instr);
        g2.drawString(instr, instrX, boxY + 110);

        g2.setFont(pixelify22);
        String triesText = "Sisa kesempatan: " + gp.fishingMiniGame.getTries();
        int triesX = getXforCenteredText(triesText);
        g2.drawString(triesText, triesX, boxY + 170);

        g2.setFont(pixelify36);
        String inputText = "Input: ";
        int inputVal = gp.fishingMiniGame.getInput();
        if (inputVal == 0) {
            inputText += "_";
        } else {
            inputText += inputVal;
        }

        int inputBoxWidth = 320;
        int inputBoxHeight = 60;
        int inputBoxX = boxX + (boxWidth - inputBoxWidth) / 2;
        int inputBoxY = boxY + 210;

        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(inputBoxX, inputBoxY, inputBoxWidth, inputBoxHeight, 18, 18);

        g2.setColor(new Color(205, 133, 63, 255));
        g2.drawRoundRect(inputBoxX, inputBoxY, inputBoxWidth, inputBoxHeight, 18, 18);

        g2.setColor(Color.WHITE);
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
        
        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(x, y, popupWidth, popupHeight, 18, 18);
        
        g2.setColor(new Color(205, 133, 63, 255));
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawRoundRect(x, y, popupWidth, popupHeight, 18, 18);

        
        int iconX = x + 10;
        int iconY = y + 6;
        int iconSize = 40;
        g2.setColor(new Color(90, 58, 42, 200)); 
        g2.fillOval(iconX-2, iconY-2, iconSize+4, iconSize+4);
        if (onhand.getImage() != null) {
            g2.setClip(iconX, iconY, iconSize, iconSize);
            g2.drawImage(onhand.getImage(), iconX, iconY, iconSize, iconSize, null);
            g2.setClip(null);
        }

        
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
        int boxX = (gp.screenWidth - boxWidth) / 2;
        int boxY = (gp.screenHeight - boxHeight) / 2 ;

        
        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        
        g2.setColor(new Color(205, 133, 63, 255));
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 36, 36);

        
        g2.setFont(pixelify30);
        g2.setColor(Color.WHITE);

        
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

        
        if (item != null && item.getImage() != null) {
            int imgW = 64, imgH = 64;
            int imgX = boxX + (boxWidth - imgW) / 2;
            int imgY = boxY + 110;
            g2.drawImage(item.getImage(), imgX, imgY, imgW, imgH, null);
        }

        
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
        
        g2.setColor(new Color(139, 69, 19, 240));
        g2.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.fillRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        g2.setColor(new Color(205, 133, 63, 255)); 
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(invX, invY, invWidth, invHeight, 30, 30);
        g2.drawRoundRect(binX, binY, binWidth, binHeight, 30, 30);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Inventory";
        int titleX = invX + (invWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, invY + 45);

        String bintitle = "Shipping Bin";
        int bintitleX = binX + (binWidth - g2.getFontMetrics().stringWidth(bintitle)) / 2; 
        g2.drawString(bintitle, bintitleX, binY + 45);

        g2.setColor(new Color(90, 58, 42, 200));
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

            
            g2.setColor(new Color(90, 58, 42, 200));
            g2.fillRoundRect(x, y, slotSize, slotSize, 14, 14);

            
            if (i == gp.keyH.cursorIndex) {
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

        if(gp.player.getOnhandItem() == null) {
            g2.setColor(Color.WHITE);
            g2.setFont(pixelify26);

            String binitem = "Select an Item";
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2 ;
            g2.drawString(binitem, binitemX, binY + 90 + 10);
        }

        else if (gp.player.getOnhandItem() != null && gp.player.getOnhandItem() instanceof items.buysellable ) {   
            Item onhand = gp.player.getOnhandItem();
            g2.setFont(pixelify22);
            String binitem = "nama : " + onhand.getName();
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            g2.drawString(binitem, binitemX, binY + 90 + 10);
                        if (onhand instanceof items.seed) {
                String binprice = "item ini tidak bisa dijual!";
                int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
                g2.drawString(binprice, binpriceX, binY + 135 + 10);
            } else {
                String binprice = "harga : " + ((items.buysellable)onhand).getHargaJual();
                int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
                g2.drawString(binprice, binpriceX, binY + 135 + 10);
                String binoption = "apakah anda yakin";
                int binoptionX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
                g2.drawString(binoption, binoptionX, binY + 180 + 10);
                String binoption2 = "ingin menjual item ini? (Y/N)";
                int binoption2X = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
                g2.drawString(binoption2, binoption2X, binY + 210 + 10);
            }

        }

        else if (gp.player.getOnhandItem() != null && !(gp.player.getOnhandItem() instanceof items.buysellable)) {
            g2.setFont(pixelify22);
            String binitem = "nama : " + gp.player.getOnhandItem().getName();
            int binitemX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            g2.drawString(binitem, binitemX, binY + 90 + 10);

            String binprice = "item ini tidak bisa dijual!";
            int binpriceX = binX + (binWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            g2.drawString(binprice, binpriceX, binY + 135 + 10);

        }
    }

    public void drawStore() {
        int cols = 4;
        int rows = 4;
        int slotSize = 80;
        int slotGapX = 48;
        int slotGapY = 48;

        int storeWidth = cols * slotSize + (cols - 1) * slotGapX + 60;
        int storeHeight = rows * slotSize + (rows - 1) * slotGapY + 180;
        int storeX = gp.screenWidth / 2 - 60;
        int storeY = gp.screenHeight / 2 - storeHeight / 2 - 30;

        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(storeX, storeY, storeWidth, storeHeight, 30, 30);

        g2.setColor(new Color(205, 133, 63, 255));
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(storeX, storeY, storeWidth, storeHeight, 30, 30);

        Shape oldClip = g2.getClip();
        g2.setClip(storeX, storeY, storeWidth, storeHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(pixelify40);
        String title = "Store";
        int titleX = storeX + (storeWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        g2.drawString(title, titleX, storeY + 45);

        int totalGridWidth = cols * slotSize + (cols - 1) * slotGapX;
        int startX = storeX + (storeWidth - totalGridWidth) / 2;
        int startY = storeY + 70;

        List<? extends Item> storeItems = null;

        if (gp.store != null) {
            storeItems = gp.store.getCurrentCategoryItems();
        }
        int maxSlots = cols * rows;

        if (storeItems != null) {
            for (int i = 0; i < storeItems.size() && i < maxSlots; i++) {
                Item item = storeItems.get(i);
                int col = i % cols;
                int row = i / cols;
                int x = startX + col * (slotSize + slotGapX);
                int y = startY + row * (slotSize + slotGapY + 12);

                g2.setColor(new Color(90, 58, 42, 200)); 
                g2.fillRoundRect(x, y, slotSize, slotSize, 14, 14);

                if (gp.store != null && i == gp.store.getCurrentItemIndex()) {
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
                if (itemName.length() > 10) {
                    itemName = itemName.substring(0, 10) + "...";
                }
                int nameWidth = g2.getFontMetrics().stringWidth(itemName);
                g2.drawString(itemName, x + (slotSize - nameWidth) / 2, y + slotSize + 20);

                g2.setFont(pixelify12);
                g2.setColor(Color.GREEN);
                int price = item instanceof items.buysellable ? ((items.buysellable)item).getHargaBeli() : 0;
                String priceText = price + "g";
                int priceWidth = g2.getFontMetrics().stringWidth(priceText);
                g2.drawString(priceText, x + (slotSize - priceWidth) / 2, y + slotSize + 35);
            }
        }

        g2.setClip(oldClip);

        int descWidth = 400;
        int descHeight = 400;
        int descX = gp.screenWidth  /2 - descWidth - 90;
        int descY = storeY ;

        g2.setColor(new Color(139, 69, 19, 240));
        g2.fillRoundRect(descX, descY, descWidth, descHeight, 30, 30);

        g2.setColor(new Color(205, 133, 63, 255)); 
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(descX, descY, descWidth, descHeight, 30, 30);

        g2.setFont(pixelify30);
        g2.setColor(Color.WHITE);
        String catTitle = "Categories";
        int catTitleX = descX + (descWidth - g2.getFontMetrics().stringWidth(catTitle)) / 2;
        g2.drawString(catTitle, catTitleX, descY + 40);

        if (gp.store != null) {
            List<String> categories = gp.store.getCategories();
            int currentCatIndex = gp.store.getCurrentCategoryIndex();

            for (int i = 0; i < categories.size(); i++) {
                String category = categories.get(i);
                int catY = descY + 80 + i * 40;

                if (i == currentCatIndex) {
                    g2.setColor(Color.YELLOW);
                    g2.drawString("> " + category, descX + 20, catY);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.drawString("  " + category, descX + 20, catY);
                }
            }

            int helpWidth = 400;
            int helpHeight = 260;
            int helpX = descX;
            int helpY = descY + descHeight + 20;

            g2.setColor(new Color(139, 69, 19, 240)); 
            g2.fillRoundRect(helpX, helpY, helpWidth, helpHeight, 30, 30);
            g2.setColor(new Color(205, 133, 63, 255));
            g2.setStroke(new java.awt.BasicStroke(4));
            g2.drawRoundRect(helpX, helpY, helpWidth, helpHeight, 30, 30);

            g2.setFont(pixelify26);
            g2.setColor(Color.WHITE);
            String helpTitle = "Controls";
            int helpTitleX = helpX + (helpWidth - g2.getFontMetrics().stringWidth(helpTitle)) / 2;
            g2.drawString(helpTitle, helpTitleX, helpY + 35);

            g2.setFont(pixelify18);
            g2.setColor(Color.LIGHT_GRAY);
            String[] controls = {
                "W: Move Up    S: Move Down",
                "A: Move Left  D: Move Right",
                "O: Prev Category  P: Next Category",
                "ENTER: Buy Item",
                "ESC: Exit Store"
            };

            for (int i = 0; i < controls.length; i++) {
                g2.drawString(controls[i], helpX + 20, helpY + 70 + i * 30);
            }

            g2.setFont(pixelify22);
            g2.setColor(Color.YELLOW);
            String goldText = "Gold: " + gp.player.getGold() + "g";
            g2.drawString(goldText, helpX + 20, helpY + 230);
        }
    }

    public void drawCookingUI() {
        OBJ_Oven oven = (OBJ_Oven) gp.obj[0];
        int boxWidth = 600, boxHeight = 520;
        int boxX = (gp.screenWidth - boxWidth) / 2;
        int boxY = (gp.screenHeight - boxHeight) / 2;

        
        g2.setColor(new Color(139, 69, 19, 240));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 32, 32);
        g2.setColor(new Color(205, 133, 63, 255)); 
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 32, 32);

        if (gp.gameState == gp.cookingState) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
            g2.setColor(Color.WHITE);
            g2.drawString("Oven - Cooking", boxX + 180, boxY + 40);
            List<Recipe> recipes = oven.getUnlockedRecipes();
            int startY = boxY + 80;

            for (int i = 0; i < recipes.size(); i++) {
                Recipe r = recipes.get(i);
                int y = startY + i * 40;
                boolean canCook = oven.canCook(r);
                if (r.getImage() != null)
                    g2.drawImage(r.getImage(), boxX + 12, y - 28, 32, 32, null);

                g2.setColor(gp.keyH.cursorIndex == i ? Color.YELLOW : (canCook ? Color.WHITE : Color.GRAY));
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                String recipeName = r.getName();
                if (recipeName.length() > 14) {
                    recipeName = recipeName.substring(0, 14) + "..";
                }
                g2.drawString(recipeName, boxX + 52, y);
            }

            if (!recipes.isEmpty()) {
                Recipe selected = recipes.get(gp.keyH.cursorIndex);
                food hasil = null;
                for (food f : oven.foodList) {
                    if (f.getName().equals(selected.getName())) {
                        hasil = f;
                        break;
                    }
                }

                int detailX = boxX + boxWidth / 2 + 10;
                int detailY = boxY + 80;

                
                g2.setColor(new Color(90, 58, 42, 200));
                g2.fillRoundRect(detailX - 10, detailY - 10, 260, 200, 16, 16);
                g2.setColor(new Color(205, 133, 63, 255));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(detailX - 10, detailY - 10, 260, 200, 16, 16);

                if (hasil != null && hasil.getImage() != null) {
                    int imageX = detailX + (260 - 48) / 2;
                    g2.drawImage(hasil.getImage(), imageX, detailY - 8, 48, 48, null);
                }

                int bahanY = detailY + 60;
                g2.setFont(g2.getFont().deriveFont(18f));
                for (var entry : selected.getIngredients().entrySet()) {
                    String name = entry.getKey();
                    int qty = entry.getValue();
                    int invQty = name.equals("Any Fish") ? gp.player.getTotalFishCaught() : gp.player.inventoryManager.getItemQuantity(name);

                    g2.setColor(invQty >= qty ? Color.WHITE : Color.GRAY);
                    g2.drawString(name + ": " + invQty + " / " + qty, detailX + 20, bahanY);
                    bahanY += 26;
                }

                List<String> fuels = oven.getAvailableFuels();
                int fuelBoxX = detailX - 10;
                int fuelBoxY = detailY + 200;
                int fuelBoxWidth = 260;
                int fuelBoxHeight = 90;

                
                g2.setColor(new Color(90, 58, 42, 200));
                g2.fillRoundRect(fuelBoxX, fuelBoxY, fuelBoxWidth, fuelBoxHeight, 12, 12);
                g2.setColor(new Color(205, 133, 63, 255));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(fuelBoxX, fuelBoxY, fuelBoxWidth, fuelBoxHeight, 12, 12);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
                g2.drawString("Bahan Bakar:", fuelBoxX + 10, fuelBoxY + 25);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
                for (int i = 0; i < fuels.size(); i++) {
                    String fuel = fuels.get(i);
                    int fuelY = fuelBoxY + 50 + i * 30;
                    g2.setColor(gp.keyH.cursorIndex2 == i ? Color.YELLOW : Color.LIGHT_GRAY);
                    g2.drawString(fuel, fuelBoxX + 10, fuelY);
                }

                g2.setFont(g2.getFont().deriveFont(14f));
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawString("[O] Ubah Bahan Bakar  [ENTER] Masak  [ESC] Batal", boxX + boxWidth - 360, boxY + boxHeight - 16);
            }
        }
    }
        
    private void drawTopRightInfoPanel() {
        int panelWidth = 280;
        int panelHeight = 210;
        int panelX = gp.screenWidth - panelWidth - 40;
        int panelY = 20;

        g2.setColor(new Color(139, 69, 19, 240)); 
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);

        g2.setColor(new Color(205, 133, 63, 255)); 
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);

        g2.setColor(new Color(101, 67, 33, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(panelX + 6, panelY + 6, panelWidth - 12, panelHeight - 12, 14, 14);

        g2.setColor(new Color(255, 255, 255, 30));
        g2.fillRoundRect(panelX + 8, panelY + 8, panelWidth - 16, panelHeight / 3, 12, 12);

        g2.setFont(pixelify32);
        g2.setColor(Color.WHITE);
        String time = gp.timeM.getTimeString();
        int timeX = panelX + (panelWidth - g2.getFontMetrics().stringWidth(time)) / 2;
        g2.drawString(time, timeX, panelY + 33);

        g2.setFont(pixelify22);
        String season = gp.timeM.getSeason();
        int day = gp.timeM.getDay();
        String dateText = season + " - Day " + day;
        int dateX = panelX + (panelWidth - g2.getFontMetrics().stringWidth(dateText)) / 2;
        g2.drawString(dateText, dateX, panelY + 53);

        int goldSectionY = panelY + 75;
        int goldSectionHeight = 40;
        g2.setColor(new Color(184, 134, 11, 200)); 
        g2.fillRoundRect(panelX + 10, goldSectionY, panelWidth - 20, goldSectionHeight, 12, 12);

        g2.setColor(new Color(255, 215, 0, 150)); 
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(panelX + 10, goldSectionY, panelWidth - 20, goldSectionHeight, 12, 12);

        BufferedImage goldImage = null;
        try {
            goldImage = ImageIO.read(new File("res/ui/gold.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (goldImage != null) {
            int goldIconSize = 32;
            int goldIconX = panelX + 20;
            int goldIconY = goldSectionY + (goldSectionHeight - goldIconSize) / 2;
            g2.drawImage(goldImage, goldIconX, goldIconY, goldIconSize, goldIconSize, null);


            g2.setFont(pixelify26);
            g2.setColor(new Color(255, 255, 255, 255)); 
            String goldText = "" + gp.player.getGold() + "g";
            int goldTextX = goldIconX + goldIconSize + 10;
            int goldTextY = goldIconY + goldIconSize / 2 + 8;

            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(goldText, goldTextX + 1, goldTextY + 1);

            g2.setColor(Color.WHITE);
            g2.drawString(goldText, goldTextX, goldTextY);
        }

        g2.setFont(pixelify18);
        g2.setColor(new Color(255, 255, 255, 200)); 
        String currentMap = gp.tileM.mapManager.getCurrentMap();
        if (currentMap != null && !currentMap.isEmpty()) {
            String displayName = getFormattedLocationName(currentMap);
            String mapText = displayName;
            int mapX = panelX + (panelWidth - g2.getFontMetrics().stringWidth(mapText)) / 2;

            int locBgWidth = g2.getFontMetrics().stringWidth(mapText) + 16;
            int locBgX = panelX + (panelWidth - locBgWidth) / 2;
            int locBgY = panelY + 125;

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillRoundRect(locBgX, locBgY, locBgWidth, 25, 8, 8);

            g2.setColor(Color.WHITE);
            g2.drawString(mapText, mapX, locBgY + 18);
        }

    int energySectionY = goldSectionY + goldSectionHeight + 60;
    int energySectionHeight = 30;
    int energyBarWidth = panelWidth - 40;
    int energyBarHeight = 20;
    
    g2.setColor(new Color(60, 60, 60, 200));
    g2.fillRoundRect(panelX + 20, energySectionY, energyBarWidth, energyBarHeight, 10, 10);
    
    int currentEnergy = gp.player.getEnergy();
    int maxEnergy = 100;
    float energyPercent = Math.max(0, (float)currentEnergy / maxEnergy);
    int energyFillWidth = (int)(energyBarWidth * energyPercent);
    
    Color energyColor;
    if (currentEnergy > 60) {
        energyColor = new Color(76, 175, 80, 220); // Green
    } else if (currentEnergy > 20) {
        energyColor = new Color(255, 193, 7, 220); // Yellow
    } else {
        energyColor = new Color(244, 67, 54, 220); // Red
    }
    
    g2.setColor(energyColor);
    g2.fillRoundRect(panelX + 20, energySectionY, energyFillWidth, energyBarHeight, 10, 10);
    
    g2.setColor(new Color(255, 255, 255, 150));
    g2.setStroke(new BasicStroke(2));
    g2.drawRoundRect(panelX + 20, energySectionY, energyBarWidth, energyBarHeight, 10, 10);
    
    g2.setFont(pixelify18);
    g2.setColor(Color.WHITE);
    String energyText = "Energy: " + currentEnergy + "/" + maxEnergy;
    int energyTextX = panelX + (panelWidth - g2.getFontMetrics().stringWidth(energyText)) / 2;
    g2.drawString(energyText, energyTextX, energySectionY - 5);

    }

    private String getFormattedLocationName(String currentMap) {
        switch (currentMap) {
            case "farm":
            case "farm1":
            case "farm2": 
            case "farm3":
            case "farm4":
            case "farm5":
                String farmVariant = gp.tileM.mapManager.getSelectedFarmMap();
                if (farmVariant.equals("farm")) {
                    return "Your Farm";
                } else {
                    return "Your Farm (" + farmVariant.toUpperCase() + ")";
                }
            case "insideHouse":
                return "Your House";
            case "town":
                return "Town";
            case "mountainlake":
                return "Mountain Lake";
            case "forestriver":
                return "Forest River";
            case "ocean":
                return "Ocean";
            case "store":
                return "Store";
            default:
                return currentMap;
        }
    }

    public void drawSetupGameInfo(){
        switch (subState) {
            case 0: setupGameInfo_top(); break;
            case 1: break;
            case 2: break;
        }
    }
    
    public void setupGameInfo_top() {
        BufferedImage backgroundImage = null;
        BufferedImage userImage = null;

        int frameWidth = gp.tileSize * 10;
        int frameHeight = gp.tileSize * 9;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 + 40;

        try {
            backgroundImage = ImageIO.read(new File("res/menu/menuScreen.png"));
            userImage = ImageIO.read(new File("res/player/player_down_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight, 1f);

        g2.setFont(pixelify50);
        String text = "WELCOME TO";
        int textX = getXforCenteredText(text);
        int textY = gp.tileSize;
        g2.drawString(text, textX, textY);

        if (userImage != null) {
            g2.drawImage(userImage, textX - 2 * gp.tileSize, textY + 2 * gp.tileSize, 2 * gp.tileSize, 2 * gp.tileSize, null);
        }

        g2.setFont(pixelify80);
        textY += gp.tileSize;
        String text2 = "SPAKBOR HILLS";
        textX = getXforCenteredText(text2);
        g2.drawString(text2, textX, textY);

        g2.setFont(pixelify32);
        textY += gp.tileSize + 10;
        String text3 = "Name";
        textX = getXforCenteredText(text3) - gp.tileSize - 15;
        g2.drawString(text3, textX, textY);

        textY += gp.tileSize / 2 + 25;
        String text6 = "Gender";
        g2.drawString(text6, textX, textY);

        textY += gp.tileSize / 2 + 15;
        String text4 = "Farm";
        g2.drawString(text4, textX, textY);
        textY += gp.tileSize - 42;
        String text5 = "Name";
        g2.drawString(text5, textX, textY);

        textX += 2 * gp.tileSize;
        textY = 3 * gp.tileSize - 25;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        int width = 250;
        int height = 50;
        text5 = "Ucok";

        g2.setColor(new Color(146, 102, 37));
        g2.fillRect(textX, textY, width, height);

        String inputText = "";

        if (setupGameInfoNum == 0) {
            String nameInput = gp.setupGame.getInput();
            inputText = nameInput.isEmpty() ? "_" : nameInput;

            g2.setColor(new Color(134, 52, 19));
            g2.fillRect(textX, textY, width, height);
            int inputX = textX + 10;
            int inputY = textY + 35;

            g2.setColor(Color.WHITE);
            gp.setupGame.setName(nameInput);
            g2.drawString(inputText, inputX, inputY);
        }

        int inputX1 = textX + 10;
        int inputY1 = textY + 35;
        textX -= 148;
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(gp.setupGame.getName(), inputX1, inputY1);
        textX += 148;

        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, width, height);

        textY += 58;
        g2.setColor(new Color(146, 102, 37));
        g2.fillRect(textX, textY, width, height);

        if (setupGameInfoNum == 1) {
            g2.setColor(new Color(134, 52, 19));
            g2.fillRect(textX, textY, width, height);
        }

        String texture = isMale ? "Male" : "Female";
        textX -= 148;
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(texture, textX + 160, textY + 35);
        textX += 148;

        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, width, height);

        textY += 57;
        g2.setColor(new Color(146, 102, 37));
        g2.fillRect(textX, textY, width, height);

        String inputText2 = "";

        if (setupGameInfoNum == 2) {
            String nameInput2 = gp.setupGame.getInput2();
            inputText2 = nameInput2.isEmpty() ? "_" : nameInput2;

            g2.setColor(new Color(134, 52, 19));
            g2.fillRect(textX, textY, width, height);
            int inputX = textX + 10;
            int inputY = textY + 35;

            g2.setColor(Color.WHITE);
            gp.setupGame.setFarmName(nameInput2);
            g2.drawString(inputText2, inputX, inputY);
        }

        int inputX = textX + 10;
        int inputY = textY + 35;
        textX -= 148;
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(gp.setupGame.getFarmName(), inputX, inputY);
        textX += 148;

        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, width, height);

        textY += 80;
        width += 200;
        height += 50;
        int xis = (gp.screenWidth - width) / 2;
        g2.setColor(new Color(146, 102, 37));
        g2.fillRect(xis, textY, width, height);

        if (setupGameInfoNum == 3) {
            g2.setColor(new Color(134, 52, 19));
            g2.fillRect(xis, textY, width, height);
        }

        textX -= 148;
        g2.setColor(new Color(255, 255, 255));
        g2.setFont(pixelify50);
        g2.drawString("START THE GAME", xis + 37, textY + 70);
        textX += 148;

        g2.setColor(new Color(0, 0, 0));
        g2.drawRect(xis, textY, width, height);
    }
            
    public void drawEndGameStats() {
        int frameWidth = gp.tileSize * 10;   
        int frameHeight = gp.tileSize * 9;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = (gp.screenHeight - frameHeight) / 2 + 10; 

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int x = frameX + 24;
        int y = frameY + 48; 
        int line = 26;       

        g2.setFont(pixelify32); 
        g2.setColor(Color.WHITE);

        
        String title = "END GAME STATISTICS";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        int centerX = frameX + (frameWidth - titleWidth) / 2;
        g2.drawString(title, centerX, y);
        y += line + 12;

        g2.setFont(pixelify16);
        g2.drawString("Total Income: " + gp.player.getTotalIncome() + "g", x, y); y += line;
        g2.drawString("Total Expenditure: " + gp.player.getTotalExpenditure() + "g", x, y); y += line;
        g2.drawString("Avg Season Income: " + gp.player.getAverageSeasonIncome() + "g", x, y); y += line;
        g2.drawString("Avg Season Expenditure: " + gp.player.getAverageSeasonExpenditure() + "g", x, y); y += line;
        g2.drawString("Total Days Played: " + gp.timeM.getTotalDaysPlayed(), x, y); y += line + 6;

        g2.setFont(pixelify18);
        g2.drawString("NPCs Status:", x, y); y += line;
        g2.setFont(pixelify12);
        for (object.OBJ_NPC npc : gp.player.allNPCs) {
            String npcLine = "- " + npc.name + " | " +
                npc.getRelationshipStatus() +
                " | Chat: " + npc.chatFrequency +
                " | Gift: " + npc.giftFrequency;
            g2.drawString(npcLine, x + 10, y);
            y += 18;
        }
        y += 6;

        int commonFishCount = 0;
        int regularFishCount = 0;
        int legendaryFishCount = 0;
        for (items.Item i : gp.player.inventoryManager.getInventory().keySet()) {
            if (i instanceof items.fish) {
                items.fish f = (items.fish) i;
                if (f.getRarity().equals("common")) {
                    commonFishCount += gp.player.inventoryManager.getItemQuantity(i.getName());
                } else if (f.getRarity().equals("regular")) {
                    regularFishCount += gp.player.inventoryManager.getItemQuantity(i.getName());
                } else if (f.getRarity().equals("legendary")) {
                    legendaryFishCount += gp.player.inventoryManager.getItemQuantity(i.getName());
                }
            }
        }

        g2.setFont(pixelify16);
        g2.drawString("Crops Harvested: " + gp.player.getTotalHarvest(), x, y); y += line;
        g2.drawString("Fish Caught: " + gp.player.getTotalFishCaught(), x, y); y += line;
        g2.setFont(pixelify12);
        g2.drawString("  - Common: " + commonFishCount, x + 18, y); y += 16;
        g2.drawString("  - Regular: " + regularFishCount, x + 18, y); y += 16;
        g2.drawString("  - Legendary: " + legendaryFishCount, x + 18, y); y += 16;

        g2.setFont(pixelify16);
        g2.setColor(Color.YELLOW);
        g2.drawString("Tekan ESC untuk tutup", x, frameY + frameHeight - 18);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}