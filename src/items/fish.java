package items;
import entity.Player;
import java.util.List;

public class fish extends Item implements buysellable, consumable, cookable{
    List<String> location;
    List<String> seasons;
    List<String> time;
    List<String> weather;
    String rarity;
    
    public void getinfo(){
        System.out.println("Fish [location=" + location + ", seasons=" + seasons + ", time=" + time + ", weather=" + weather + ", rarity=" + rarity + "]");
    }

    public void buy(Item item) {
        // Implement the buy logic here
        System.out.println("Buying " + item.name);
    }

    public void sell(Item item) {
        // Implement the sell logic here
        System.out.println("Selling " + item.name);
    }

    public void consume(Player player) {
        // Implement the consume logic here
        System.out.println("Consuming " + name);
    }
    public void cook(Item item) {
        // Implement the cook logic here
        System.out.println("Cooking " + name);
    }
    
}