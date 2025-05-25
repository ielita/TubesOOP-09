package items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public abstract class Item {
    // Common properties for all items
    private String name;
    private BufferedImage image;
    public GamePanel gp;
    
    // Constructor
    public Item(String name, GamePanel gp) {
        this.gp = gp;
        this.name = name;
        this.image = loadImageFromResource();
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage loadImageFromResource() {
        try {
            String path = "/res/items/" + name + ".png";
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading item image: " + name);
            return null;
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Abstract method that all items must implement
    public abstract void getinfo();
}