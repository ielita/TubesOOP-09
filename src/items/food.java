package items;

import entity.Player;
import main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class food extends Item implements consumable, buysellable {
    private int energy;
    private int hargaBeli;
    private int hargaJual;
    private BufferedImage image;

    public food(String name,  GamePanel gp, int energy, int hargaBeli, int hargaJual) {
        super(name, gp);
        this.energy = energy;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.image = loadImage(name);
    }

    private BufferedImage loadImage(String name) {
        try {
            // Nama file: res/recipe/[nama makanan].png (spasi diganti _, lowercase)
            String fileName = name + ".png";
            return ImageIO.read(new File("res/items/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        }
    }

    public BufferedImage getImage() { return image; }

    public int getEnergy() { return energy; }
    public int getHargaBeli() { return hargaBeli; }
    @Override
    public int getHargaJual() { return hargaJual; }

    @Override
    public void consume(Player player) {
        player.setEnergy(player.getEnergy() + energy);
        player.inventoryManager.removeItem(player.getOnhandItem(), 1);
    }

    public void getinfo() {
        System.out.println("Food [name=" + getName() + ", energy=" + energy + ", hargaBeli=" + hargaBeli + ", hargaJual=" + hargaJual + "]");
    }
    
    public boolean canBeBought() {
        return hargaBeli > 0;
    }
}