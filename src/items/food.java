package items;
import entity.Player;
import main.GamePanel;

public class food extends Item implements consumable, buysellable {
    private int hargabeli;
    private int hargajual;
    private int energi;

    public food(String name, GamePanel gp, int energi, int hargabeli, int hargajual) {
        super(name, gp);
        this.energi = energi;
        this.hargabeli = hargabeli;
        this.hargajual = hargajual;
    }


    public int getHargaBeli() {
        return hargabeli;
    }

    @Override
    public int getHargaJual() {
        return hargajual;
    }

    public int getEnergi() {
        return energi;
    }

    public void sethargabeli(int hargabeli) {
        this.hargabeli = hargabeli;
    }

    public void sethargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public void setEnergi(int energi) {
        this.energi = energi;
    }

    public boolean canBeBought() {
        return hargabeli > 0;
    }


    @Override
    public void consume(Player player) {

    }


    @Override
    public void getinfo() {

}
}