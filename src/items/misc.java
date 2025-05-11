package items;

public class misc extends Item implements buysellable {
    public int hargabeli;
    public int hargajual;
    public int gethargabeli(){
        return hargabeli;
    }
    public int gethargajual(){
        return hargajual;
    }
    public void sethargabeli(int hargabeli){
        this.hargabeli =hargabeli;
    }
    public void sethargajual(int hargajual){
        this.hargajual = hargajual;
    }
}