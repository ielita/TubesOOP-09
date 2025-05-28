package items;

import entity.Player; // Replace 'your.package' with the actual package name where Player is defined
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
        this.hargabeli =hargabeli;
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
        // TODO: Implement consume logic
        System.out.println("Crop consumed by player.");
    }

    @Override
    public void cook(Item item) {
        // TODO: Implement cook logic
        System.out.println("Crop cooked.");
    }

    @Override
    public void sell(Item item) {
        // TODO: Implement sell logic
        System.out.println("Crop sold.");
    }

    @Override
    public void buy(Item item) {
        // TODO: Implement buy logic
        System.out.println("Crop bought.");
    }
    
    public void getinfo(){
        System.out.println("Crop [hargabeli=" + hargabeli + ", hargajual=" + hargajual + ", jumlahCropPanen=" + jumlahCropPanen + "]");
    }
}