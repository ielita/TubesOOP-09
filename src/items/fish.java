package items;
import entity.Player;
import main.GamePanel;

import java.util.List;

public class fish extends Item implements buysellable, consumable, cookable{
    private List<String> location;
    private List<String> seasons;
    private List<String> time;
    private List<String> weather;
    private String rarity;

    public fish(String name, List<String> location, List<String> seasons, List<String> time, List<String> weather, String rarity, GamePanel gp) {
        super(name, gp);
        this.location = location;
        this.seasons = seasons;
        this.time = time;
        this.weather = weather;
        this.rarity = rarity;
    }
    
    public void getinfo(){
        System.out.println("Fish [location=" + location + ", seasons=" + seasons + ", time=" + time + ", weather=" + weather + ", rarity=" + rarity + "]");
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getWeather() {
        return weather;
    }

    public void setWeather(List<String> weather) {
        this.weather = weather;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getHargaBeli() {
        return 0;
    }

    @Override
    public int getHargaJual() {
        int seasonCount = seasons.size();
        int weatherCount = weather.size();
        int locationCount = location.size();

        int totalHours = 0;
        for (String t : time) {
            String[] parts = t.split("–");
            if (parts.length == 2) {
                int start = Integer.parseInt(parts[0].replace(".", ""));
                int end = Integer.parseInt(parts[1].replace(".", ""));
                int startHour = start / 100;
                int endHour = end / 100;
                int durasi = endHour >= startHour ? endHour - startHour : (24 - startHour + endHour);
                totalHours += durasi;
            }
        }
        if (totalHours == 0) totalHours = 24;

        int C = 10;
        if ("Regular".equalsIgnoreCase(rarity)) C = 5;
        if ("Legendary".equalsIgnoreCase(rarity)) C = 25;

        // Rumus harga jual
        double harga = (4.0 / seasonCount) * (24.0 / totalHours) * (2.0 / weatherCount) * (4.0 / locationCount) * C;
        return (int) Math.round(harga);
    }


    public void consume(Player player) {
    }
    
    public void cook(Item item) {
    }
}