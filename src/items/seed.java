package items;

public class seed extends Item implements buysellable {
    private int hargabeli;
    private int hargajual;
    private String cropType; // What crop this seed will grow into
    private int growthTime; // Time needed to grow into a crop

    public seed(String name, String description, String cropType, int growthTime) {
        this.name = name;
        this.description = description;
        this.cropType = cropType;
        this.growthTime = growthTime;
        this.hargabeli = 0; // Default value
        this.hargajual = 0; // Default value
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

    // Implement buysellable interface methods
    @Override
    public void sell(Item item) {
        System.out.println("Selling seed: " + name + " for " + hargajual + " coins");
    }

    @Override
    public void buy(Item item) {
        System.out.println("Buying seed: " + name + " for " + hargabeli + " coins");
    }

    // Implement abstract method from Item class
    @Override
    public void getinfo() {
        System.out.println("Seed [name=" + name + 
                         ", cropType=" + cropType + 
                         ", growthTime=" + growthTime + 
                         ", hargabeli=" + hargabeli + 
                         ", hargajual=" + hargajual + "]");
    }
}