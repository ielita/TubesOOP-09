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

    @Override
    public int getHargaJual() {
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

    // Calculate estimated profit from this seed
    public int getEstimatedProfit() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "parsnip": return 35 - hargabeli;
            case "cauliflower": return 150 - hargabeli;
            case "potato": return 80 - hargabeli;
            case "wheat": return (30 * 3) - hargabeli;  // 3 crops per harvest
            case "blueberry": return (40 * 3) - hargabeli;  // 3 berries per harvest
            case "tomato": return 60 - hargabeli;
            case "hot pepper": return 40 - hargabeli;
            case "melon": return 250 - hargabeli;
            case "cranberry": return (25 * 10) - hargabeli;  // 10 cranberries per harvest
            case "pumpkin": return 250 - hargabeli;
            case "grape": return (10 * 20) - hargabeli;  // 20 grapes per harvest
            default: return 0;
        }
    }

    // Get crop yield when harvested
    public int getCropYield() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "wheat": return 3;
            case "blueberry": return 3;
            case "cranberry": return 10;
            case "grape": return 20;
            default: return 1;
        }
    }

    // Get the sell price for the resulting crop
    public int getCropSellPrice() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "parsnip": return 35;
            case "cauliflower": return 150;
            case "potato": return 80;
            case "wheat": return 30;
            case "blueberry": return 40;
            case "tomato": return 60;
            case "hot pepper": return 40;
            case "melon": return 250;
            case "cranberry": return 25;
            case "pumpkin": return 250;
            case "grape": return 10;
            default: return 0;
        }
    }

    // Get the buy price for the resulting crop
    public int getCropBuyPrice() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "parsnip": return 50;
            case "cauliflower": return 200;
            case "potato": return 0; // Can't buy potato
            case "wheat": return 50;
            case "blueberry": return 150;
            case "tomato": return 90;
            case "hot pepper": return 0; // Can't buy hot pepper
            case "melon": return 0; // Can't buy melon
            case "cranberry": return 0; // Can't buy cranberry
            case "pumpkin": return 300;
            case "grape": return 100;
            default: return 0;
        }
    }

    // Plant this seed and return the resulting crop when ready
    public crop plant() {
        // Create the crop that will be harvested from this seed
        crop harvestedCrop = new crop(
            this.cropType,  // Crop name (e.g., "Parsnip")
            this.gp,
            getCropBuyPrice(),
            getCropSellPrice(), 
            getCropYield()
        );
        
        System.out.println("Planted " + this.getName() + ". Will grow " + this.cropType + " in " + this.growthTime + " days.");
        System.out.println("Expected yield: " + getCropYield() + " " + this.cropType);
        return harvestedCrop;
    }

    // Calculate total revenue from one harvest
    public int getTotalRevenue() {
        return getCropSellPrice() * getCropYield();
    }

    // Calculate profit percentage
    public double getProfitPercentage() {
        if (hargabeli == 0) return 0;
        return ((double) getEstimatedProfit() / hargabeli) * 100;
    }

    // Check if this is a multi-harvest crop
    public boolean isMultiHarvest() {
        String cropLower = cropType.toLowerCase();
        return cropLower.equals("wheat") || cropLower.equals("blueberry") || 
               cropLower.equals("cranberry") || cropLower.equals("grape");
    }

    // Implement buysellable interface methods
    @Override
    public void sell(Item item) {
        System.out.println("Selling " + getName() + " for " + hargajual + "g");
    }

    @Override
    public void buy(Item item) {
        System.out.println("Buying " + getName() + " for " + hargabeli + "g");
    }

    // Implement abstract method from Item class
    @Override
    public void getinfo() {
        System.out.println("=== Seed Information ===");
        System.out.println("Name: " + getName());
        System.out.println("Crop Type: " + cropType);
        System.out.println("Growth Time: " + growthTime + " days");
        System.out.println("Buy Price: " + hargabeli + "g");
        System.out.println("Sell Price: " + hargajual + "g");
        System.out.println("Crop Yield: " + getCropYield() + " per harvest");
        System.out.println("Crop Sell Price: " + getCropSellPrice() + "g each");
        System.out.println("Total Revenue: " + getTotalRevenue() + "g");
        System.out.println("Estimated Profit: " + getEstimatedProfit() + "g");
        System.out.println("Profit Percentage: " + String.format("%.1f", getProfitPercentage()) + "%");
        System.out.println("Multi-Harvest: " + (isMultiHarvest() ? "Yes" : "No"));
        System.out.println("Description: " + description);
        System.out.println("========================");
    }
}