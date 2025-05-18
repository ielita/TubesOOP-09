package items;

public class food extends Item implements consumable, buysellable {
    int hargabeli;
    int hargajual;
    int energi;

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
}