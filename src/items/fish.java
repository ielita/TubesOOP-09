package items;
import java.util.ArrayList;
import java.util.List;
public class fish extends Item implements buysellable, consumable, cookable{
    List<String> location;
    List<Season> seasons;
    List<Time> time;
    List<Weather> weather;
    String rarity;
    
}