package items;

import java.util.Map;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import main.GamePanel;
import javax.imageio.ImageIO;

public class Recipe extends Item implements buysellable {
    public enum UnlockType {
        DEFAULT, STORE, FISH_COUNT, ITEM_OBTAIN, HARVEST, FISH_SPECIFIC
    }

    private String id;
    private Map<String, Integer> ingredients;
    private UnlockType unlockType;
    private String unlockParam;
    private boolean unlocked;
    private BufferedImage image;

    public Recipe(String id, String name, Map<String, Integer> ingredients, UnlockType unlockType, String unlockParam, boolean unlocked, BufferedImage image, GamePanel gp) {
        super(name, gp);
        this.id = id;
        this.ingredients = ingredients;
        this.unlockType = unlockType;
        this.unlockParam = unlockParam;
        this.unlocked = unlocked;
        if (image != null) {
            this.image = image;
        } else {
            this.image = loadImage(name);
        }
    }

    public Recipe(String id, String name, Map<String, Integer> ingredients, UnlockType unlockType, String unlockParam, boolean unlocked, GamePanel gp) {
        this(id, name, ingredients, unlockType, unlockParam, unlocked, null, gp);
    }

    private BufferedImage loadImage(String name) {
        try {
            String fileName = id + ".png";
            return ImageIO.read(new File("res/recipe/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getId() { return id; }
    public Map<String, Integer> getIngredients() { return ingredients; }
    public UnlockType getUnlockType() { return unlockType; }
    public String getUnlockParam() { return unlockParam; }
    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
    public BufferedImage getImage() { return image; }
    public void setImage(BufferedImage image) { this.image = image; }

    @Override
    public void getinfo() {
        System.out.println("Recipe [id=" + id + ", name=" + getName() + ", ingredients=" + ingredients + ", unlockType=" + unlockType + ", unlockParam=" + unlockParam + ", unlocked=" + unlocked + "]");
    }

    @Override
    public int getHargaBeli() {
        return 50; 
    }

    public int getHargaJual() {
        return 0; 
    }
}