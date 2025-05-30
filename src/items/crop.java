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

    public int getHargaBeli(){
        return hargabeli;
    }
    
    @Override
    public int getHargaJual(){
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

    // Implement abstract methods
    @Override
    public void consume(Player player) {
        int energyGain = calculateEnergyGain();
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
    public void cook(Item item) {

    }

    @Override
    public void getinfo() {
    }
}