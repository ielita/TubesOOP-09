package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import items.*;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;
    public boolean showDebug = false;
    public boolean sprintPressed = false;
    public boolean enterPressed = false;
    public int menuOption = 0;
    public int inventoryCursorIndex = 0;
    private final int NUM_OPTIONS = 4;
    // Add menu option
    public int options = 0;
    private final int OPTIONS = 2; // Continue and Main Menu

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        if(gp.gameState == gp.menuState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                menuOption--;
                if(menuOption < 0) {
                    menuOption = NUM_OPTIONS - 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                menuOption++;
                if(menuOption >= NUM_OPTIONS) {
                    menuOption = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                switch(menuOption) {
                    case 0: 
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        break;
                    case 1: // Options
                        // Add options menu later
                        break;
                    case 2: break; 
                    case 3: // Exit
                        System.exit(0);
                        break;
                }
            }
        }

        if(gp.gameState == gp.optionsState) {
            int maxCommandNum = 0;
            switch(gp.ui.subState){
                case 0: maxCommandNum = 4; break; 
            }
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                // gp.playSE(9);
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = maxCommandNum;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                // gp.playSE(9);
                if(gp.ui.commandNum > maxCommandNum) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {

                enterPressed = true;
            }
        }

        if (gp.gameState == gp.keyBindingState){
            int maxKeyBindNum = 0;
            switch(gp.ui.subState){
                case 0: maxKeyBindNum = 0; break; 
            }
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.keyBindNum--;
                // gp.playSE(9);
                if(gp.ui.keyBindNum < 0) {
                    gp.ui.keyBindNum = maxKeyBindNum;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.keyBindNum++;
                // gp.playSE(9);
                if(gp.ui.keyBindNum > maxKeyBindNum) {
                    gp.ui.keyBindNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {

                enterPressed = true;
            }
        }

        if (gp.gameState == gp.playState && code == KeyEvent.VK_J) {
            gp.gameState = gp.inventoryState;
            inventoryCursorIndex = 0; 
            return;

        } else if (gp.gameState == gp.inventoryState && code == KeyEvent.VK_J) {
            gp.gameState = gp.playState;
            return;
        }

        if (gp.gameState == gp.inventoryState) {
            int invSize = gp.player.getInventory().size();
            int cols = 5;

            if (code == KeyEvent.VK_D && inventoryCursorIndex + 1 < invSize && (inventoryCursorIndex + 1) % cols != 0) {
                inventoryCursorIndex++;
            }

            if (code == KeyEvent.VK_A && inventoryCursorIndex % cols != 0) {
                inventoryCursorIndex--;
            }
            
            if (code == KeyEvent.VK_W && inventoryCursorIndex - cols >= 0) {
                inventoryCursorIndex -= cols;
            }
            
            if (code == KeyEvent.VK_S && inventoryCursorIndex + cols < invSize) {
                inventoryCursorIndex += cols;
            }
            
            if (code == KeyEvent.VK_ENTER) {
                List<Map.Entry<Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
                entries.sort((a, b) -> {
                    boolean aEquip = a.getKey() instanceof items.equipment;
                    boolean bEquip = b.getKey() instanceof items.equipment;
                    if (aEquip && !bEquip) return -1;
                    if (!aEquip && bEquip) return 1;
                    return 0;
                });
                if (!entries.isEmpty() && inventoryCursorIndex < entries.size()) {
                    Item selected = entries.get(inventoryCursorIndex).getKey();
                    gp.player.setOnhandItem(selected);
                    System.out.println("Selected item: " + gp.player.getOnhandItem().getName());
                }
                gp.gameState = gp.playState;
            }
            return; 
        }
        
        if (gp.gameState != gp.playState) {
            return;
        }

        if (code == KeyEvent.VK_W ){//|| code == KeyEvent.VK_UP
            upPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE){
            if (gp.gameState == gp.playState){
                gp.gameState = gp.optionsState;
            }
            else if(gp.gameState == gp.optionsState){
                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_O){
            showDebug = !showDebug;
        }

        if (code == KeyEvent.VK_SHIFT){
            sprintPressed = true;
        }

        if (code == KeyEvent.VK_I){
            interactPressed = true;
        }

        if (gp.gameState == gp.playState && code == KeyEvent.VK_F) {
            Item onhand = gp.player.getOnhandItem();
            if (onhand != null && onhand instanceof items.equipment) {
                ((items.equipment)onhand).use(gp.player);
            } else if (onhand != null && onhand instanceof items.seed) {
                gp.player.plantSeed();
            }
        }

        // Add this cheat code
        if (code == KeyEvent.VK_1) {
            // Skip one day cheat
            gp.timeM.skipDay();
            System.out.println("Day skipped! New date: " + gp.timeM.getDateString());
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

        int code = e.getKeyCode();
                if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        } 
        if (code == KeyEvent.VK_I){
            interactPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            sprintPressed = false;
        }
    }

}