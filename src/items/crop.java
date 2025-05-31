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
        player.setEnergy(player.getEnergy() + 3);
        player.inventoryManager.removeItem(player.getOnhandItem(), 1);
        gp.timeM.setMinute(gp.timeM.getMinute() + 5);
    }

    @Override
    public void getinfo() {

    }

}