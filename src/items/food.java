package items;

public class food extends item implements consumable, buysellable{
    int hargabeli;
    int hargajual;
    int energi;
    public int gethargabeli(){
        return hargabeli;
    }
    public int gethargajual(){
        return hargajual;
    }
    public int getEnergi(){
        return energi;
    }
    public void sethargabeli(int hargabeli){
        this.hargabeli =hargabeli;
    }
    public void sethargajual(int hargajual){
        this.hargajual = hargajual;
    }
    public void setEnergi(int energi){
        this.energi = energi;
    }
}