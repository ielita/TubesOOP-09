package items;

import java.awt.image.BufferedImage;
import main.GamePanel;

public abstract class Item {
    // Common properties for all items
    public String name;
    public BufferedImage image;
    public String description;
    public int quantity;
    public GamePanel gp;
    
    // World coordinates for when item is in the world
    public int worldX;
    public int worldY;
    
    // Constructor
    public Item() {
        this.gp = gp;// Default quantity
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;

    
    // Abstract method that all items must implement
    public abstract void getinfo();

}
}