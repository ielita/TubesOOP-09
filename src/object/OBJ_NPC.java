package object;

import java.util.List;

public class OBJ_NPC extends SuperObject {

    private int heartPoints = 0;
    private final int maxHeartPoints = 150;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus = "single";
    private String location;

    public OBJ_NPC(String name, String location, List<String> lovedItems, List<String> likedItems, List<String> hatedItems) {
        this.name = name;
        this.location = location;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
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
            
        }
    }

    public String evaluateGift(String itemName) {
        if (lovedItems.contains(itemName)) {
            increaseHeartPoints(20);
            return name + " loves this gift! (+20 heart points)";
        } else if (likedItems.contains(itemName)) {
            increaseHeartPoints(10);
            return name + " likes this gift. (+10 heart points)";
        } else if (hatedItems.contains(itemName)) {
            increaseHeartPoints(-10);
            return name + " hates this gift... (-10 heart points)";
        } else {
            increaseHeartPoints(0);
            return name + " feels neutral about this.";
        }
    }
}
