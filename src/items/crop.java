package items;

public class crop extends Item implements consumable, buysellable, cookable{
    int hargabeli;
    int hargajual;
    int jumlahCropPanen;
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
}