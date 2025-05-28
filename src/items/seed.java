package items;
import main.GamePanel;

public class seed extends Item implements buysellable {
    private int hargabeli;
    private int hargajual;
    private String cropType;
    private int growthTime;
    private String description;


    public seed(String name, GamePanel gp, String description, String cropType, int growthTime) {
        super(name, gp);
        this.description = description;
        this.cropType = cropType;
        this.growthTime = growthTime;
        this.hargabeli = 100; // Default buy price
        this.hargajual = 50;  // Default sell price
    }

    // Getters and setters
    public int getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(int hargabeli) {
        this.hargabeli = hargabeli;
    }

    public int getHargajual() {
        return hargajual;
    }

    public void setHargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public String getCropType() {
        return cropType;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public String getDescription() {
        return description;
    }

    // Implement buysellable interface methods
    @Override
    public void sell(Item item) {

    }

    @Override
    public void buy(Item item) {
    }

    // Implement abstract method from Item class
    @Override
    public void getinfo() {

    }
}