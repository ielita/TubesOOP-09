package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Rectangle;

import main.GamePanel;
import items.*;

public class NPC extends Entity{
    private int heartPoints;
    private String name;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus;
    protected String message;

    public NPC(GamePanel gp, String name, int heartPoints, 
               List<String> lovedItems, List<String> likedItems, 
               List<String> hatedItems, String relationshipStatus) {
        super(gp);

        direction = "down";
        speed = 1; 
        getImage();
        solidArea = new Rectangle();
        solidArea.x = 32;   
        solidArea.y =32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        collisionOn = true;
        this.name = name;
        this.heartPoints = heartPoints;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        this.relationshipStatus = relationshipStatus;
    
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }  

    public int getHeartPoints() {
        return heartPoints;
    }

    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
    }

    public List<String> getLovedItems() {
        return lovedItems;
    }

    public void setLovedItems(List<String> lovedItems) {
        this.lovedItems = lovedItems;
    }

    public List<String> getLikedItems() {
        return likedItems;
    }

    public void setLikedItems(List<String> likedItems) {
        this.likedItems = likedItems;
    }

    public List<String> getHatedItems() {
        return hatedItems;
    }

    public void setHatedItems(List<String> hatedItems) {
        this.hatedItems = hatedItems;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void getImage() {

        abigail1 = setup("npc/abigail/abigail-idle1");
        
    }

    public void chatting() {
    }       
    public void giveGift(String item) {
        if (lovedItems.contains(item)) {
            heartPoints += 10;
            System.out.println(name + " loves this gift! Heart points increased.");
        } else if (likedItems.contains(item)) {
            heartPoints += 5;
            System.out.println(name + " likes this gift! Heart points increased.");
        } else if (hatedItems.contains(item)) {
            heartPoints -= 5;
            System.out.println(name + " hates this gift! Heart points decreased.");
        } else {
            System.out.println(name + " has no strong feelings about this gift.");
        }
    }

    public void displayInfo() {
        System.out.println("NPC Name: " + name);
        System.out.println("Heart Points: " + heartPoints);
        System.out.println("Loved Items: " + lovedItems);
        System.out.println("Liked Items: " + likedItems);
        System.out.println("Hated Items: " + hatedItems);
        System.out.println("Relationship Status: " + relationshipStatus);
    }


    public void draw(Graphics2D g2){

        BufferedImage image = null;
        image = abigail1;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
        }
        
    protected boolean isPlayerInRange(GamePanel gp, int range) {
        int playerX = gp.player.worldX + gp.player.solidArea.x;
        int playerY = gp.player.worldY + gp.player.solidArea.y;
        int objectX = worldX + solidArea.x;
        int objectY = worldY + solidArea.y;
        
        int distance = (int)Math.sqrt(
            Math.pow(playerX - objectX, 2) + 
            Math.pow(playerY - objectY, 2)
        );
        
        return distance < range;
    }

    public void onInteract() {
        System.out.println("Interacting with " + name);
        displayInfo();
    }

    public void interact(GamePanel gp, KeyHandler keyH) {
        if (keyH != null) {
            int interactionRange = (int)(gp.tileSize * 1.5);
            boolean inRange = isPlayerInRange(gp, interactionRange);
            
            if (inRange && keyH.NPCInteractPressed) {
                onInteract();
            }
            
            message = String.format("Range: %b, Interact: %b", 
                inRange, keyH.NPCInteractPressed);
        } else {
            message = "Error: handlers not initialized";
        }

        if (collisionOn) {
            // if (gp.keyH.NPCInteractPressed) {
            //     System.out.println("Player is trying to interact with NPC: " + name);
            // }
            gp.player.interactNPC();
            gp.keyH.NPCInteractPressed = false;
            System.out.println("Player is interacting with NPC: " + name);
        }
    }
    
    public void talk(){
        setHeartPoints(heartPoints+10);
        System.out.println("Talking to " + name + ". Heart points increased to " + heartPoints);
    }
}