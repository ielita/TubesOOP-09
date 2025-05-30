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

    @Override
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
        return switch (cropName) {
            case "parsnip" -> 25;
            case "cauliflower" -> 35;
            case "potato" -> 30;
            case "wheat" -> 15;
            case "blueberry" -> 20;
            case "tomato" -> 25;
            case "hot pepper" -> 40;
            case "melon" -> 45;
            case "cranberry" -> 15;
            case "pumpkin" -> 50;
            case "grape" -> 10;
            default -> 20;
        }; // Spicy gives more energy
        // Large fruit gives more energy
        // Large vegetable
    }



    @Override
    public void cook(Item item) {

    }

    @Override
    public void getinfo() {
    }
}