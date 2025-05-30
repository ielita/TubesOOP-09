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
        player.setEnergy(player.getEnergy() + 3);
        player.inventoryManager.removeItem(player.getOnhandItem(), 1);
    }

    @Override
    public void getinfo() {

    }

}