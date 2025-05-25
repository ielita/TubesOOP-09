package items;
import entity.Player;
import main.GamePanel;

public class food extends Item implements consumable, buysellable {
    int hargabeli;
    int hargajual;
    int energi;

    public food(String name, GamePanel gp ,int energi) {
        super(name, gp);
        this.energi = energi;
        this.hargabeli = 0; // Default value
        this.hargajual = 0; // Default value
    }

    public int gethargabeli() {
        return hargabeli;
    }

    public int gethargajual() {
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

    @Override
    public void getinfo() {
        // Provide a suitable implementation for getinfo()
        System.out.println( "Food [hargabeli=" + hargabeli + ", hargajual=" + hargajual + ", energi=" + energi + "]");
    }
    public void consume(Player player) {
        // Implement the consume logic here
        System.out.println("Consuming " + getName());
    }

    public void buy(Item item) {
        // Implement the buy logic here
        System.out.println("Buying " + getName());
    }

    public void sell(Item item) {
        // Implement the sell logic here
        System.out.println("Selling " + getName());
    }

}