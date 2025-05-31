package entity;

import items.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import main.GamePanel;
import main.InventoryManager;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private int defaultSpeed;
    private int sprintSpeed;
    private int energy;
    private int gold;
    private int totalFishCaught;
    public InventoryManager fishCaught;
    private int coalUseCount = 0; 
    private boolean hasHarvested;
    public InventoryManager inventoryManager;
    public List<Recipe> recipesUnlocked = RecipeData.getAllRecipes(gp);
    public boolean wentOut;
    public boolean relationshipStatus;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        wentOut = false;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        inventoryManager = new InventoryManager();
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        getImage();
        setDefaultValues();
        initializeStartingItems(); // Add this line
    }

    public void setDefaultValues(){
        // Default spawn position can be overridden by map changes
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 2;
        speed = defaultSpeed;  // Use default speed initially
        direction = "down";
        spriteNum = 1;
        defaultSpeed = 10; 
        sprintSpeed = 15; 
        energy = 100; 
        totalFishCaught = 0; 
        gold = 500; 
        hasHarvested = false;
        relationshipStatus = false; 
    }

    // Add this method to Player class
    private void initializeStartingItems() {
        equipment hoe = new equipment("Hoe", gp);
        addItemToInventory(hoe, 1); 
        
        equipment pickaxe = new equipment("Pickaxe", gp);
        addItemToInventory(pickaxe, 1); 
        
        equipment wateringCan = new equipment("Watering Can", gp);
        addItemToInventory(wateringCan, 1); 
        
        equipment sickle = new equipment("Sickle", gp);
        addItemToInventory(sickle, 1); 

        equipment FishingRod = new equipment("Fishing Rod", gp);
        addItemToInventory(FishingRod, 1);
        
        fishCaught = new InventoryManager();

        seed parsnipSeed = SeedData.getSeedByName(gp, "Parsnip Seeds");
        if (parsnipSeed != null) {
            addItemToInventory(parsnipSeed, 10); 
        }
        
        seed potatoSeed = SeedData.getSeedByName(gp, "Potato Seeds");
        if (potatoSeed != null) {
            addItemToInventory(potatoSeed, 5); 
        }

        seed wheatSeed = SeedData.getSeedByName(gp, "Wheat Seeds");
        if (wheatSeed != null) {
            addItemToInventory(wheatSeed, 3); 
        }
        misc firewood = new misc("Firewood", gp,10,5);
        addItemToInventory(firewood, 10); 
        misc coal = new misc("Coal", gp, 15, 8);
        addItemToInventory(coal, 5); 
        crop grape = new crop( "Grape", gp,100, 10, 20);
        addItemToInventory(grape, 5);
    }

    public int getGold() {
        return gold;
    }

    public void setCoalUseCount(int count) {
        this.coalUseCount = count;
    }

    public int getCoalUseCount() {
        return coalUseCount;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public int getTotalFishCaught() {
        return totalFishCaught;
    }

    public void setTotalFishCaught(int totalFishCaught) {
        this.totalFishCaught = totalFishCaught;
    }

    public boolean hasHarvested() {
        return hasHarvested;
    }
    
    public void setHasHarvested(boolean hasHarvested) {
        this.hasHarvested = hasHarvested;
    }
    
    public void addItemToInventory(Item item, int quantity) {
        inventoryManager.addItem(item, quantity);
    }

    public void removeItemFromInventory(Item item, int quantity) {
        inventoryManager.removeItem(item, quantity);
    }

    public Item getOnhandItem() {
        return inventoryManager.getOnhandItem();
    }

    public void setOnhandItem(Item item) {
        inventoryManager.setOnhandItem(item);
    }

    public void plantSeed() {
        if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
            return;
        }
        
        Item onhand = getOnhandItem();
        if (onhand instanceof seed) {
            seed seedToPlant = (seed) onhand;
            

            int currentTile = getFacingTile();

            if (currentTile == 7 || currentTile == 9) {
                gp.tileM.mapManager.initializePlantTracking();
                
                int[] coordinates = getFacingTileCoordinates();
                int col = coordinates[0];
                int row = coordinates[1];
                
                if (currentTile == 7) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 8;
                } else if (currentTile == 9) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 10;
                }
                
                gp.tileM.mapManager.plantedSeeds[col][row] = seedToPlant;
                gp.tileM.mapManager.plantGrowth[col][row] = seedToPlant.getGrowthTime();
                
                removeItemFromInventory(seedToPlant, 1);
                
                if (!getInventory().containsKey(seedToPlant) || getInventory().get(seedToPlant) <= 0) {
                    setOnhandItem(null);
                }
                
                reduceEnergy(5);

            }
        }
    }

    public void setPosition(int x, int y) {
        worldX = gp.tileSize * x;
        worldY = gp.tileSize * y;
    }


    public Map<Item, Integer> getInventory() {
        return inventoryManager.getInventory();
    }

    public void getImage() {

        up1 = setup("player/player_up_1");
        up2 = setup("player/player_up_2");
        upidle = setup("player/player_up_3");
        downidle = setup("player/player_down_3");
        down1 = setup("player/player_down_1");
        down2 = setup("player/player_down_2");
        left1 = setup("player/player_left_1");
        left2 = setup("player/player_left_2");
        left3 = setup("player/player_left_3");

        right3 = setup("player/player_right_3");
        right1 = setup("player/player_right_1");
        right2 = setup("player/player_right_2");
    }

    public void update() {
        speed = keyH.sprintPressed ? sprintSpeed : defaultSpeed;

        if (keyH.interactPressed == true) {
            for(int i = 0; i < gp.obj.length; i++) {
                if(gp.obj[i] != null) {
                    gp.obj[i].interact(gp, keyH);
                }
            }
        }

        if (keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);

            if(collisionOn == false) {
                switch(direction){
                    case "up":
                        worldY-=speed;
                        break;
                    case "down":
                        worldY+=speed;
                        break;
                    case "left":
                        worldX-=speed;
                        break;
                    case "right":
                        worldX+=speed;
                        break;
                }
            }

            int animationSpeed = keyH.sprintPressed ? 4 : 8;
            spriteCounter++;
            if (spriteCounter > animationSpeed) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 3;
                }
                else if(spriteNum == 3) {
                    spriteNum = 4;
                }
                else if(spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            spriteNum = 4;
        }
    }

    public int getFacingTile() {
        int playerCenterX = worldX + solidArea.x + solidArea.width / 2;
        int playerCenterY = worldY + solidArea.y + solidArea.height / 2;
        
        int facingX = playerCenterX;
        int facingY = playerCenterY;
        
        switch (direction) {
            case "up":    facingY -= gp.tileSize; break;
            case "down":  facingY += gp.tileSize; break;
            case "left":  facingX -= gp.tileSize; break;
            case "right": facingX += gp.tileSize; break;
        }
        
        int col = facingX / gp.tileSize;
        int row = facingY / gp.tileSize;
        
        if (col < 0 || row < 0 || col >= gp.tileM.mapManager.maxWorldCol || row >= gp.tileM.mapManager.maxWorldRow) return -1;
        int tileNum = gp.tileM.mapManager.mapTileNum[col][row];

        return tileNum;
    }

    public int[] getFacingTileCoordinates() {
        int playerCenterX = worldX + solidArea.x + solidArea.width / 2;
        int playerCenterY = worldY + solidArea.y + solidArea.height / 2;

        int facingX = playerCenterX;
        int facingY = playerCenterY;

        switch (direction) {
            case "up":    facingY -= gp.tileSize; break;
            case "down":  facingY += gp.tileSize; break;
            case "left":  facingX -= gp.tileSize; break;
            case "right": facingX += gp.tileSize; break;
        }

        int col = facingX / gp.tileSize;
        int row = facingY / gp.tileSize;

        return new int[]{col, row};
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = upidle;
                }
                if (spriteNum == 3){
                    image = up2;
                }
                if (spriteNum == 4){
                    image = upidle;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = downidle;
                }
                if (spriteNum == 3){
                    image = down2;
                }
                if (spriteNum == 4){
                    image = downidle;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left3;
                }
                if (spriteNum == 3){
                    image = left2;
                }
                if (spriteNum == 4){
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right3;
                }
                if (spriteNum == 3){
                    image = right2;
                }
                if (spriteNum == 4){
                    image = right3;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);

    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(-20, Math.min(100, energy)); 

        if (this.energy <= -20) {
            forceCollapse();
        }
    }

    public void addEnergy(int amount) {
        setEnergy(energy + amount);
    }

    public void reduceEnergy(int amount) {
        setEnergy(energy - amount);
    }

    public void sleep() {
        gp.ui.startSleepAnimation();
        
        if (object.OBJ_ShippingBin.goldEarned > 0) {
            int goldFromShipping = object.OBJ_ShippingBin.goldEarned;
            addGold(goldFromShipping);
            object.OBJ_ShippingBin.goldEarned = 0; 
        }
        
        if (getEnergy() > 10) {
            setEnergy(100);
        } else if (getEnergy() > 0 && getEnergy() <= 10) {
            setEnergy(50);
        } else if (getEnergy() <= 0) {
            setEnergy(10);
        }
        
        gp.timeM.skipDay();

    }

    private void forceCollapse() {
        if (object.OBJ_ShippingBin.goldEarned > 0) {
            int goldFromShipping = object.OBJ_ShippingBin.goldEarned;
            addGold(goldFromShipping);
            object.OBJ_ShippingBin.goldEarned = 0;
        }
        
        gp.ui.startSleepAnimation();
        
        this.energy = 10;
        
        gp.timeM.skipDay();

    }
}
