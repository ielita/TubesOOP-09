package object;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_NPC extends SuperObject {

    private int heartPoints = 0;
    private final int maxHeartPoints = 150;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus = "single";
    private String location;
    public GamePanel gp;

    public OBJ_NPC(String name, String location, List<String> lovedItems, List<String> likedItems, List<String> hatedItems, GamePanel gp) {
        this.gp = gp;
        this.name = name;
        this.location = location;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        String imagePath = "res/npc/ninja.png";
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

    public String getLocation() {
        return location;
    }

    @Override
    protected void onInteract() {
        if (collision) {
            gp.keyH.menuOption = 0;
            gp.gameState = gp.npcInteractionState;
        }
}

    public void evaluateGift(String itemName) {
        if (lovedItems.contains(itemName)) {
            increaseHeartPoints(20);
        } else if (likedItems.contains(itemName)) {
            increaseHeartPoints(10);
        } else if (hatedItems.contains(itemName)) {
            increaseHeartPoints(-10);
        } else {
            increaseHeartPoints(0);
        }
    }
}
