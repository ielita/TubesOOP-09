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

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private int defaultSpeed = 10;
    private int sprintSpeed = 15;
    private int energy = 100;
    private int gold = 500;  // Change this line - start with 500 gold
    private String farmName;
    public InventoryManager inventoryManager; // Add this line

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        // Add this line
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

    // Add this method to Player class
    private void initializeStartingItems() {
        // Add starting equipment
        equipment hoe = new equipment("Hoe", gp);
        addItemToInventory(hoe, 1);
        System.out.println("Added Hoe");
        
        equipment pickaxe = new equipment("Pickaxe", gp);
        addItemToInventory(pickaxe, 1);
        System.out.println("Added Pickaxe");
        
        equipment wateringCan = new equipment("Watering Can", gp);
        addItemToInventory(wateringCan, 1);
        System.out.println("Added Watering Can");
        
        equipment sickle = new equipment("Sickle", gp);
        addItemToInventory(sickle, 1);
        System.out.println("Added Sickle");

        equipment fishingRod = new equipment("Fishing Rod", gp);
        addItemToInventory(fishingRod, 1);
        System.out.println("Added Fishing Rod");
        
        // Add starting seeds
        seed parsnipSeed = SeedData.getSeedByName(gp, "Parsnip Seeds");
        if (parsnipSeed != null) {
            addItemToInventory(parsnipSeed, 10);
            System.out.println("Added 10 Parsnip Seeds");
        }
        
        seed potatoSeed = SeedData.getSeedByName(gp, "Potato Seeds");
        if (potatoSeed != null) {
            addItemToInventory(potatoSeed, 5);
            System.out.println("Added 5 Potato Seeds");
        }

        seed wheatSeed = SeedData.getSeedByName(gp, "Wheat Seeds");
        if (wheatSeed != null) {
            addItemToInventory(wheatSeed, 3);
            System.out.println("Added 3 Wheat Seeds");
        }
        

        
        System.out.println("Starting gold: " + getGold() + "g");
        System.out.println("Starting items initialized!");
    }

    // Add these methods to Player class
    public int getGold() {
        return gold;
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

    // Plant seed method
    public void plantSeed() {
        // Hanya bisa plant di farm
        if (!"farm".equals(gp.tileM.mapManager.getCurrentMap())) {
            System.out.println("You can only plant seeds on your farm!");
            return;
        }
        
        Item onhand = getOnhandItem();
        if (onhand instanceof seed) {
            seed seedToPlant = (seed) onhand;
            
            // Calculate tile position
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

            // Check bounds
            if (!gp.tileM.mapManager.isValidPosition(col, row)) {
                System.out.println("Cannot plant here - out of bounds!");
                return;
            }

            int currentTile = gp.tileM.mapManager.mapTileNum[col][row];

            // Check if tile is tilled
            if (currentTile == 7 || currentTile == 9) {
                // Initialize plant tracking if not done yet
                gp.tileM.mapManager.initializePlantTracking();
                
                // Plant the seed
                if (currentTile == 7) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 8;
                } else if (currentTile == 9) {
                    gp.tileM.mapManager.mapTileNum[col][row] = 10;
                }
                
                // Store seed info
                gp.tileM.mapManager.plantedSeeds[col][row] = seedToPlant;
                gp.tileM.mapManager.plantGrowth[col][row] = seedToPlant.getGrowthTime();
                
                // Remove seed from inventory
                removeItemFromInventory(seedToPlant, 1);
                
                System.out.println("Planted " + seedToPlant.getName() + "!");
                System.out.println("Will be ready in " + seedToPlant.getGrowthTime() + " days.");
                
                // Clear onhand if no more seeds
                if (!getInventory().containsKey(seedToPlant) || getInventory().get(seedToPlant) <= 0) {
                    setOnhandItem(null);
                }
            } else {
                System.out.println("You need to till the soil first!");
            }
        } else {
            System.out.println("You need to select seeds first!");
        }
    }

    public void setDefaultValues(){
        // Default spawn position can be overridden by map changes
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 2;
        speed = defaultSpeed;  // Use default speed initially
        direction = "down";
        spriteNum = 1;
    }

    // Add a new method to set position explicitly
    public void setPosition(int x, int y) {
        worldX = gp.tileSize * x;
        worldY = gp.tileSize * y;
    }

    // Replace existing inventory methods with these delegate methods
    public Map<Item, Integer> getInventory() {
        return inventoryManager.getInventory();
    }



    public void getImage() {

        up1 = setup("player/player_up_1");
        up2 = setup("player/player_up_2");
        down1 = setup("player/player_down_1");
        down2 = setup("player/player_down_2");
        left1 = setup("player/player_left_1");
        left2 = setup("player/player_left_2");
        right1 = setup("player/player_right_1");
        right2 = setup("player/player_right_2");
    }

    public void update() {
        // Update speed based on sprint key
        speed = keyH.sprintPressed ? sprintSpeed : defaultSpeed;

        // Separate interaction check from movement
        if (keyH.interactPressed == true) {
            // Check all objects for possible interactions
            for(int i = 0; i < gp.obj.length; i++) {
                if(gp.obj[i] != null) {
                    gp.obj[i].interact(gp, keyH);
                }
            }
            keyH.interactPressed = false;
        }

        // Movement checks
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

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
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

            // Faster animation when sprinting
            int animationSpeed = keyH.sprintPressed ? 8 : 12;
            spriteCounter++;
            if (spriteCounter > animationSpeed) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public boolean isFacingWater() {
        // Use same calculation method as plantSeed for consistency
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
        
        if (col < 0 || row < 0 || col >= gp.tileM.mapManager.maxWorldCol || row >= gp.tileM.mapManager.maxWorldRow) return false;
        int tileNum = gp.tileM.mapManager.mapTileNum[col][row];

        return tileNum == 6;
    }

    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);

        // g2.fillRect(worldX, worldY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);

    }

    // Add these energy methods to Player class
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(100, energy)); // Keep energy between 0-100
    }

    public void addEnergy(int amount) {
        setEnergy(energy + amount);
    }

    public void reduceEnergy(int amount) {
        setEnergy(energy - amount);
    }
}