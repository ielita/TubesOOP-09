package items;

import entity.Player;
import main.GamePanel;

public class crop extends Item implements consumable, buysellable, cookable{
    int hargabeli;
    int hargajual;
    int jumlahCropPanen;

    public crop(String name,GamePanel gp, int hargabeli, int hargajual, int jumlahCropPanen) {
        super(name, gp);
        this.hargabeli = hargabeli;
        this.hargajual = hargajual;
        this.jumlahCropPanen = jumlahCropPanen;
    }

    public int gethargabeli(){
        return hargabeli;
    }
    
    public int gethargajual(){
        return hargajual;
    }
    
    public int getjumlahCropPanen(){
        return jumlahCropPanen;
    }
    
    public void sethargabeli(int hargabeli){
        this.hargabeli = hargabeli;
    }
    
    public void sethargajual(int hargajual){
        this.hargajual = hargajual;
    }
    
    public void setjumlahCropPanen(int jumlahCropPanen){
        this.jumlahCropPanen = jumlahCropPanen;
    }

    // Calculate total value of this crop stack
    public int getTotalValue() {
        return hargajual * jumlahCropPanen;
    }

    // Get value per individual crop
    public int getValuePerCrop() {
        return hargajual;
    }

    // Check if this crop can be bought from shops
    public boolean canBeBought() {
        return hargabeli > 0;
    }

    // Get profit if bought and then sold
    public int getBuySellProfit() {
        if (!canBeBought()) return 0;
        return (hargajual - hargabeli) * jumlahCropPanen;
    }

    // Implement abstract methods
    @Override
    public void consume(Player player) {
        // Add energy or health to player based on crop type
        int energyGain = calculateEnergyGain();
        // player.addEnergy(energyGain); // Uncomment when Player has energy system
        
        System.out.println("Consumed " + getName() + "!");
        System.out.println("Gained " + energyGain + " energy!");
        
        // Reduce quantity after consumption
        if (jumlahCropPanen > 0) {
            jumlahCropPanen--;
        }
    }

    private int calculateEnergyGain() {
        String cropName = getName().toLowerCase();
        
        // Energy gain based on crop type
        switch (cropName) {
            case "parsnip": return 25;
            case "cauliflower": return 35;
            case "potato": return 30;
            case "wheat": return 15;
            case "blueberry": return 20;
            case "tomato": return 25;
            case "hot pepper": return 40; // Spicy gives more energy
            case "melon": return 45; // Large fruit gives more energy
            case "cranberry": return 15;
            case "pumpkin": return 50; // Large vegetable
            case "grape": return 10;
            default: return 20;
        }
    }

    @Override
    public void sell(Item item) {
        System.out.println("Selling " + jumlahCropPanen + " " + getName() + " for " + getTotalValue() + "g total");
        System.out.println("(" + hargajual + "g each)");
    }

    @Override
    public void buy(Item item) {
        if (canBeBought()) {
            System.out.println("Buying " + jumlahCropPanen + " " + getName() + " for " + (hargabeli * jumlahCropPanen) + "g total");
            System.out.println("(" + hargabeli + "g each)");
        } else {
            System.out.println(getName() + " cannot be purchased from shops!");
        }
    }

    @Override
    public void cook(Item item) {
        System.out.println("Cooking " + getName() + "...");
        
        // Different cooking methods based on crop type
        String cropName = getName().toLowerCase();
        switch (cropName) {
            case "wheat":
                System.out.println("Grinding wheat into flour...");
                break;
            case "tomato":
                System.out.println("Making tomato sauce...");
                break;
            case "potato":
                System.out.println("Baking potato...");
                break;
            case "pumpkin":
                System.out.println("Making pumpkin pie...");
                break;
            case "grape":
                System.out.println("Fermenting grapes into wine...");
                break;
            default:
                System.out.println("Preparing " + getName() + " dish...");
                break;
        }
        
        System.out.println("Cooking completed! Nutritional value increased!");
    }

    @Override
    public void getinfo() {
        System.out.println("=== Crop Information ===");
        System.out.println("Name: " + getName());
        System.out.println("Quantity: " + jumlahCropPanen);
        System.out.println("Buy Price: " + (canBeBought() ? hargabeli + "g each" : "Cannot be bought"));
        System.out.println("Sell Price: " + hargajual + "g each");
        System.out.println("Total Value: " + getTotalValue() + "g");
        System.out.println("Energy Gain: " + calculateEnergyGain() + " per crop");
        System.out.println("Can be cooked: Yes");
        System.out.println("Can be consumed: Yes");
        if (canBeBought()) {
            System.out.println("Buy-Sell Profit: " + getBuySellProfit() + "g");
        }
        System.out.println("========================");
    }
}