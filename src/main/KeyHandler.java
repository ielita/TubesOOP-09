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
            int cols = 8; // Sesuaikan dengan UI
            int rows = 4;
            int maxSlots = cols * rows;
            int visibleSize = Math.min(invSize, maxSlots);

            if (code == KeyEvent.VK_D && inventoryCursorIndex + 1 < visibleSize && (inventoryCursorIndex + 1) % cols != 0) {
                inventoryCursorIndex++;
            }

            if (code == KeyEvent.VK_A && inventoryCursorIndex % cols != 0) {
                inventoryCursorIndex--;
            }
            
            if (code == KeyEvent.VK_W && inventoryCursorIndex - cols >= 0) {
                inventoryCursorIndex -= cols;
            }
            
            if (code == KeyEvent.VK_S && inventoryCursorIndex + cols < visibleSize) {
                inventoryCursorIndex += cols;
            }
            
            if (code == KeyEvent.VK_ENTER) {
                List<Map.Entry<Item, Integer>> entries = new ArrayList<>(gp.player.getInventory().entrySet());
                entries.sort((a, b) -> {
                    boolean aEquip = a.getKey() instanceof items.equipment;
                    boolean bEquip = b.getKey() instanceof items.equipment;
                    if (aEquip && !bEquip) return -1;
                    if (!aEquip && bEquip) return 1;
                    return a.getKey().getName().compareToIgnoreCase(b.getKey().getName());
                });
                if (!entries.isEmpty() && inventoryCursorIndex < entries.size() && inventoryCursorIndex < maxSlots) {
                    Item selected = entries.get(inventoryCursorIndex).getKey();
                    gp.player.setOnhandItem(selected);
                    System.out.println("Selected item: " + gp.player.getOnhandItem().getName());
                }
                gp.gameState = gp.playState;
            }
            return; 
        }

        if (gp.gameState == gp.fishingMiniGameState && gp.fishingMiniGame.isActive()) {
            // Input angka, hanya set input (belum submit)
            if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
                int num = code - KeyEvent.VK_0;
                // Untuk range > 10, allow multi-digit input
                if (gp.fishingMiniGame.getMax() > 10) {
                    int current = gp.fishingMiniGame.getInput();
                    if (current < 1000) { // prevent overflow
                        gp.fishingMiniGame.setInput(current * 10 + num);
                    }
                } else {
                    gp.fishingMiniGame.setInput(num);
                }
            } else if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
                gp.fishingMiniGame.setInput(0); // hapus input sebelum submit
            }
            // Submit hanya saat ENTER
            if (code == KeyEvent.VK_ENTER && gp.fishingMiniGame.getInput() != 0) {
                int guess = gp.fishingMiniGame.getInput();
                if (guess == gp.fishingMiniGame.getAnswer()) {
                    gp.player.addItemToInventory(gp.fishingMiniGame.getPendingFish(), 1);
                    System.out.println("Benar! Kamu dapat ikan: " + gp.fishingMiniGame.getPendingFish().getName());
                    gp.fishingMiniGame.finish();
                    gp.gameState = gp.playState;
                } else {
                    gp.fishingMiniGame.decTries();
                    if (gp.fishingMiniGame.getTries() <= 0) {
                        System.out.println("Gagal! Kesempatan habis.");
                        gp.fishingMiniGame.finish();
                        gp.gameState = gp.playState;
                    } else {
                        System.out.println("Salah! Sisa kesempatan: " + gp.fishingMiniGame.getTries());
                        gp.fishingMiniGame.resetInput(); // reset input untuk percobaan berikutnya
                    }
                }
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

        if (code == KeyEvent.VK_I) {
            Item onhand = gp.player.getOnhandItem();
            if (onhand != null && onhand instanceof items.equipment) {
                ((items.equipment)onhand).use(gp.player);
            } else if (onhand != null && onhand instanceof items.seed) {
                gp.player.plantSeed(); // Plant seed
            }
            interactPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            ePressed = true;
        }
        if (code == KeyEvent.VK_J) {
            gp.gameState = gp.inventoryState;
            inventoryCursorIndex = 0;
        }
        
        // Remove harvest key since sickle already handles it

        // Debug keys
        if (code == KeyEvent.VK_1) {
            gp.timeM.skipDay();
            System.out.println("Day skipped! New date: " + gp.timeM.getDateString());
        }
        if (code == KeyEvent.VK_2) {
            gp.player.addGold(100);
            System.out.println("Added 100 gold! Total: " + gp.player.getGold() + "g");
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