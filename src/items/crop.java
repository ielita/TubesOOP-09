package items;

import entity.Player;
import main.GamePanel;

public class crop extends Item implements consumable, buysellable{
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
    
    @Override
    public int getHargaJual(){
        return hargajual;
    }

    @Override
    public int getHargaBeli(){
        return hargabeli;
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
    public void getinfo() {

    }

}