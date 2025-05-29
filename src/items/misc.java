package items;
import entity.Player;
import main.GamePanel;

public class misc extends Item implements buysellable {
    private int hargabeli;
    private int hargajual;
    private String category;

    public misc(String name, GamePanel gp, String type, int hargabeli, int hargajual) {
        super(name, gp);
        this.category = category;
        this.hargabeli = hargabeli;
        this.hargajual = hargajual;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int getHargaBeli() {
        return hargabeli;
    }

    @Override
    public int getHargaJual() {
        return hargajual;
    }

    public void sethargabeli(int hargabeli) {
        this.hargabeli = hargabeli;
    }

    public void sethargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    @Override
    public void getinfo() {
    }
}