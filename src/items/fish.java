package items;
import java.util.ArrayList;
import java.util.List;
public class fish extends item implements buysellable, consumable, cookable{
    List<String> location;
    List<Season> seasons;
    List<Time> time;
    List<Weather> weather;
    String rarity;
    
}