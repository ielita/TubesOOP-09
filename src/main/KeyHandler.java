package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;
    public boolean showDebug = false;
    public boolean sprintPressed = false;
    public boolean ePressed = false;
    public int menuOption = 0;
    public int inventoryCursorIndex = 0;
    private final int NUM_OPTIONS = 3;
    // Add pause menu option
    public int pauseOption = 0;
    private final int PAUSE_OPTIONS = 2; // Continue and Main Menu

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
                    case 0: // Start Game
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        break;
                    case 1: // Options
                        // Add options menu later
                        break;
                    case 2: // Exit
                        System.exit(0);
                        break;
                }
            }
        }

        if(gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                pauseOption--;
                if(pauseOption < 0) {
                    pauseOption = PAUSE_OPTIONS - 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                pauseOption++;
                if(pauseOption >= PAUSE_OPTIONS) {
                    pauseOption = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                switch(pauseOption) {
                    case 0: // Continue
                        gp.gameState = gp.playState;
                        break;
                    case 1: // Main Menu
                        gp.gameState = gp.menuState;
                        pauseOption = 0; // Reset pause menu selection
                        gp.stopMusic(); // Stop game music
                        break;
                }
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
                java.util.List<items.Item> items = new java.util.ArrayList<>(gp.player.getInventory().keySet());
                if (!items.isEmpty() && inventoryCursorIndex < items.size()) {
                    gp.player.setOnhandItem(items.get(inventoryCursorIndex));
                    System.out.println("Selected item: " + items.get(inventoryCursorIndex).getName());
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
                gp.gameState = gp.pauseState;
            }
            else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_O){
            showDebug = !showDebug;
        }

        if (code == KeyEvent.VK_P){
            sprintPressed = true;
        }

        if (code == KeyEvent.VK_I){
            interactPressed = true;
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
        if (code == KeyEvent.VK_P) {
            sprintPressed = false;
        }
    }

}