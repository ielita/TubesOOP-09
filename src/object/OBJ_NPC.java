package object;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import items.fish;
import main.GamePanel;

public class OBJ_NPC extends SuperObject {

    private int heartPoints = 150;
    public int chatFrequency = 0;
    public int giftFrequency = 0;

    private final int maxHeartPoints = 150;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus = "single";
    private int fianceDay = -1;
    public GamePanel gp;

    public OBJ_NPC(String name, List<String> lovedItems, List<String> likedItems, List<String> hatedItems, GamePanel gp) {
        this.gp = gp;
        this.name = name;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        String imagePath = "res/npc/" + name.toLowerCase() + ".png";
        try {
            image = ImageIO.read(new File(imagePath));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHeartPoints() {
        return heartPoints;
    }

    public void setHeartPoints(int heartPoints) {
        this.heartPoints = Math.min(heartPoints, maxHeartPoints);
    }

    public void increaseHeartPoints(int amount) {
        this.heartPoints = Math.min(this.heartPoints + amount, maxHeartPoints);
    }

    public List<String> getLovedItems() {
        return lovedItems;
    }

    public List<String> getLikedItems() {
        return likedItems;
    }

    public List<String> getHatedItems() {
        return hatedItems;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String status) {
        this.relationshipStatus = status;
    }

    @Override
    protected void onInteract() {
        if (collision) {
            gp.keyH.menuOption = 0;
            gp.gameState = gp.npcInteractionState;
        }   
    }

    public void chat() {
        chatFrequency++;
        gp.player.reduceEnergy(10);
        increaseHeartPoints(10);
        gp.timeM.setMinute(gp.timeM.getMinute() + 10);
    }

    public void gift(String itemName, items.Item item) {
        if (item == null) {
            return; 
        }
        giftFrequency++;
        if (evaluateGift(itemName, item)) {
            gp.player.inventoryManager.removeItem(item, 1);
            gp.timeM.setMinute(gp.timeM.getMinute() + 10);
            gp.player.reduceEnergy(5);
        } 
    }

    public void propose() {
        gp.player.fianceToWho = name;
        fianceDay = gp.timeM.getDay();
        relationshipStatus = "fiance";
    }

    public void marry() {
        if (canMarryToday()) {
            gp.player.marriedToWho = name;
            relationshipStatus = "married";
            gp.player.reduceEnergy(80);
            gp.timeM.setHour(22);
            gp.timeM.setMinute(0);
            gp.tileM.mapManager.changeMap("insideHouse", 2 , 2 );
            
        }
    }

    public int getFianceDay() {
        return fianceDay;
    }
    public boolean canMarryToday() {
        return gp.timeM.getDay() > fianceDay;
    }

    public boolean evaluateGift(String itemName, items.Item item) {
        if (item instanceof items.equipment) {
            
            return false;
        }
        if (lovedItems.contains(itemName)) {
            increaseHeartPoints(25);
        } else if (likedItems.contains(itemName)) {
            increaseHeartPoints(20);
        } else if (
            (hatedItems.isEmpty() && !lovedItems.contains(itemName) && !likedItems.contains(itemName))
            || hatedItems.contains(itemName)
            || (hatedItems.contains("Fish") && item instanceof items.fish)
        ) {
            increaseHeartPoints(-25);
        } else {
            increaseHeartPoints(0);
        }
        return true; 
    }
}
