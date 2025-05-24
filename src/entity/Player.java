package entity;

import items.Item;
import items.food;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
    private String farmName;
    private InventoryManager inventoryManager;

    public Player(GamePanel gp,KeyHandler keyH){

        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 19;   
        solidArea.y = 25;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 25;
        solidArea.height = 25;
        inventoryManager = new InventoryManager();

        // test items
        Item testItem1 = new food("apel", "Buah segar",1);
        Item testItem2 = new food("ikan", "Ikan segar",1);
        Item testItem3 = new food("kntl", "Ikan segar",1);
        Item testItem4 = new food("carlen", "Ikan segar",1);
        Item testItem5 = new food("ultah", "Ikan segar",1);
        Item testItem6 = new food("anjai", "Ikan segar",1);
        inventoryManager.addItem(testItem1, 2);
        inventoryManager.addItem(testItem2, 3);
        inventoryManager.addItem(testItem2, 3);
        inventoryManager.addItem(testItem3, 3);
        inventoryManager.addItem(testItem4, 3);
        inventoryManager.addItem(testItem5, 3);
        inventoryManager.addItem(testItem6, 3);

        setDefaultValues();
        getImage();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

    


        //COLLISION AREA (player)
        // g2.setColor(Color.YELLOW);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}