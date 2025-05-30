package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import items.*;
import object.OBJ_Oven;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;
    public boolean showDebug = false;
    public boolean sprintPressed = false;
    public boolean enterPressed = false;
    public boolean escPressed = false;
    public boolean bPressed = false; 
    public boolean rPressed = false;
    public int menuOption = 0;
    public int cursorIndex = 0;
    public int cursorIndex2 = 0;
    private final int NUM_OPTIONS = 3;
    public boolean setupDone = false;
    

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        char keyChar = e.getKeyChar();

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
                        if (!setupDone) {
                            gp.gameState = gp.setupGameInfoState;
                            setupDone = true;
                        } else {
                            gp.gameState = gp.playState;
                        }
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

            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
            
            return;
        }

        if (gp.gameState == gp.setupGameInfoState) {
            if (code == KeyEvent.VK_UP) gp.ui.setupGameInfoNum = (gp.ui.setupGameInfoNum - 1 + 4) % 4;
            if (code == KeyEvent.VK_DOWN) gp.ui.setupGameInfoNum = (gp.ui.setupGameInfoNum + 1) % 4;

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.setupGameInfoNum == 3) {
                    enterPressed = true;
                    gp.gameState = gp.playState;
                }
                if (gp.ui.setupGameInfoNum == 1) {
                    enterPressed = true;
                    gp.ui.isMale = !gp.ui.isMale;
                }
            }

            if (gp.setupGame.isNameInputActive()) {
                if (code == KeyEvent.VK_BACK_SPACE) {
                    if (gp.ui.setupGameInfoNum == 0) {
                        String current = gp.setupGame.getInput();
                        if (!current.isEmpty()) gp.setupGame.setInput(current.substring(0, current.length() - 1));
                    }
                    if (gp.ui.setupGameInfoNum == 2) {
                        String current = gp.setupGame.getInput2();
                        if (!current.isEmpty()) gp.setupGame.setInput2(current.substring(0, current.length() - 1));
                    }
                } else if (Character.isLetterOrDigit(keyChar) || keyChar == ' ') {
                    if (gp.ui.setupGameInfoNum == 0 && gp.setupGame.getInput().length() < 12)
                        gp.setupGame.setInput(gp.setupGame.getInput() + keyChar);
                    if (gp.ui.setupGameInfoNum == 2 && gp.setupGame.getInput2().length() < 12)
                        gp.setupGame.setInput2(gp.setupGame.getInput2() + keyChar);
                }
                return;
            }
            return;
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
            cursorIndex = 0; 
            return;

        } else if (gp.gameState == gp.inventoryState && code == KeyEvent.VK_J) {
            gp.gameState = gp.playState;
            return;
        }

        if (code == KeyEvent.VK_ESCAPE && escPressed == false) {
            escPressed = true;
            if (gp.gameState == gp.playState){
                gp.gameState = gp.optionsState;
            }
            else if(gp.gameState == gp.optionsState){
                gp.gameState = gp.playState;
            }
        }

        if (gp.gameState == gp.cookingState) {
            OBJ_Oven oven = (OBJ_Oven) gp.obj[0];
            List<Recipe> recipes = oven.getUnlockedRecipes();

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                cursorIndex = (cursorIndex - 1 + recipes.size()) % recipes.size();
                cursorIndex2 = 0; // reset fuel index saat pindah resep
                enterPressed = false;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                cursorIndex = (cursorIndex + 1) % recipes.size();
                cursorIndex2 = 0;
                enterPressed = false;
            }

            List<String> fuels = oven.getAvailableFuels();

            if (code == KeyEvent.VK_O) {
                // O untuk geser fuel ke kanan
                cursorIndex2 = (cursorIndex2 + 1) % fuels.size();
            }

            if (code == KeyEvent.VK_ENTER && !recipes.isEmpty() && !enterPressed) {
                enterPressed = true;
                Recipe selected = recipes.get(cursorIndex);
                String selectedFuel = fuels.get(cursorIndex2);
                if (oven.canCook(selected)) {
                    oven.cook(selected, selectedFuel);
                    gp.gameState = gp.playState; // kembali ke game utama
                    enterPressed = false;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                enterPressed = false;
            }
        }


        if (gp.gameState == gp.inventoryState) {
            int invSize = gp.player.getInventory().size();
            int cols = 8; // Sesuaikan dengan UI
            int rows = 4;
            int maxSlots = cols * rows;
            int visibleSize = Math.min(invSize, maxSlots);

            if (code == KeyEvent.VK_D && cursorIndex + 1 < visibleSize && (cursorIndex + 1) % cols != 0) {
                cursorIndex++;
            }

            if (code == KeyEvent.VK_A && cursorIndex % cols != 0) {
                cursorIndex--;
            }
            
            if (code == KeyEvent.VK_W && cursorIndex - cols >= 0) {
                cursorIndex -= cols;
            }
            
            if (code == KeyEvent.VK_S && cursorIndex + cols < visibleSize) {
                cursorIndex += cols;
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

                if (!entries.isEmpty() && cursorIndex < entries.size() && cursorIndex < maxSlots) {
                    Item selected = entries.get(cursorIndex).getKey();
                    if (selected == gp.player.getOnhandItem()) {
                        gp.player.setOnhandItem(null); 
                    } else {
                        gp.player.setOnhandItem(selected);
                    }
                }
                gp.gameState = gp.playState;
            }
            return; 
        } 
                
        if (gp.gameState == gp.fishingMiniGameState && gp.fishingMiniGame.isActive()) {
            if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
                int digit = code - KeyEvent.VK_0;
                gp.fishingMiniGame.addDigit(digit);
            } else if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
                gp.fishingMiniGame.backspace();
            } else if (code == KeyEvent.VK_ENTER) {
                int input = gp.fishingMiniGame.getInput();
                if (input >= gp.fishingMiniGame.getMin() && input <= gp.fishingMiniGame.getMax()) {
                    gp.fishingMiniGame.processGuess(input, gp.player);
                    if (!gp.fishingMiniGame.isActive()) {
                        gp.gameState = gp.fishingResultState;
                    }
                }
            }
            return;
        }

        if (gp.gameState == gp.fishingResultState && code == KeyEvent.VK_ENTER) {
            gp.fishingMiniGame.finish();
            gp.gameState = gp.playState;
            return;
        }

        if (gp.gameState == gp.shippingBinState) {
            int invSize = gp.player.getInventory().size();
            int cols = 8;
            int rows = 4;
            int maxSlots = cols * rows;
            int visibleSize = Math.min(invSize, maxSlots);

            if (code == KeyEvent.VK_D && cursorIndex + 1 < visibleSize && (cursorIndex + 1) % cols != 0) {
                cursorIndex++;
            }

            if (code == KeyEvent.VK_A && cursorIndex % cols != 0) {
                cursorIndex--;
            }

            if (code == KeyEvent.VK_W && cursorIndex - cols >= 0) {
                cursorIndex -= cols;
            }

            if (code == KeyEvent.VK_S && cursorIndex + cols < visibleSize) {
                cursorIndex += cols;
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
                if (!entries.isEmpty() && cursorIndex < entries.size() && cursorIndex < maxSlots) {
                    Item selected = entries.get(cursorIndex).getKey();
                    gp.player.setOnhandItem(selected);
                }

            }
            if (code == KeyEvent.VK_Y){
                Item onhandItem = gp.player.getOnhandItem();

                if (onhandItem == null){
                    return;
                }

                if (onhandItem instanceof seed) {
                    return;
                }

                if (onhandItem instanceof buysellable){
                    buysellable sellableItem = (buysellable) onhandItem;
                    int sellPrice = sellableItem.getHargaJual();
                    String itemName = onhandItem.getName();

                    gp.player.inventoryManager.removeItem(onhandItem, 1);

                    object.OBJ_ShippingBin.goldEarned += sellPrice;
                }
            }

            if (code == KeyEvent.VK_I){
                gp.gameState = gp.playState;
            }
            return;
        }

        if (gp.gameState == gp.storeState) {
            if (code == KeyEvent.VK_W) {
                if (gp.store != null) {
                    gp.store.nextItem();
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.store != null) {
                    gp.store.prevItem();
                }
            }
            if (code == KeyEvent.VK_A) {
                if (gp.store != null) {
                    gp.store.prevItemInRow();
                }
            }
            if (code == KeyEvent.VK_D) {
                if (gp.store != null) {
                    gp.store.nextItemInRow();
                }
            }
            if (code == KeyEvent.VK_O) {
                if (gp.store != null) {
                    gp.store.prevCategory();
                }
            }
            if (code == KeyEvent.VK_P) {
                if (gp.store != null) {
                    gp.store.nextCategory();
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.store != null) {
                    Item currentItem = gp.store.getCurrentItem();
                    if (currentItem != null) {
                        try {
                            int price = ((buysellable) currentItem).getHargaBeli();
                            if (gp.player.getGold() >= price) {
                                gp.store.buyItem(currentItem);
                            }
                        } catch (ClassCastException ex) {
                            System.out.println("Item tidak dapat dibeli: " + currentItem.getName());
                        }
                    }
                }
            }
            if (code == KeyEvent.VK_B) {
                gp.gameState = gp.playState;
            }
            return;
        }

        if (gp.gameState != gp.playState) {
            return;
        }

        if (code == KeyEvent.VK_W ){
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

        if (code == KeyEvent.VK_O){
            showDebug = !showDebug;
        }

        if (code == KeyEvent.VK_B) {
            try {
                if (gp.store == null) {
                    gp.store = new Store(gp);
                }
                gp.gameState = gp.storeState;
            } catch (Exception ex) {
                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_R) {
            rPressed = true;
            if (gp.player.getOnhandItem() != null && gp.player.getOnhandItem() instanceof consumable) {
                ((consumable) gp.player.getOnhandItem()).consume(gp.player);
            }
        }

        if (code == KeyEvent.VK_SHIFT){
            sprintPressed = true;
        }

        if (code == KeyEvent.VK_I) {
            Item onhand = gp.player.getOnhandItem();
            if (onhand != null && onhand instanceof items.equipment) {
                ((items.equipment)onhand).use(gp.player);
            } else if (onhand != null && onhand instanceof items.seed) {
                gp.player.plantSeed();
            }
            interactPressed = true;
        }
   
        if (code == KeyEvent.VK_J) {
            gp.gameState = gp.inventoryState;
            cursorIndex = 0;
        }
        
        if (code == KeyEvent.VK_1) {
            gp.timeM.skipDay();
        }

        if (code == KeyEvent.VK_2) {
            gp.player.addGold(100);
        }
        
        
        if (code == KeyEvent.VK_3) {
            gp.timeM.setHour(gp.timeM.getHour() + 1);
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
        if (code == KeyEvent.VK_SHIFT) {
            sprintPressed = false;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }

        if (code == KeyEvent.VK_R) {
            rPressed = false;
        }
    }

}