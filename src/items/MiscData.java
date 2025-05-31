package items;

import main.GamePanel;
import java.util.ArrayList;
import java.util.List;

public class MiscData {
    private static List<misc> miscList = new ArrayList<>();

    public static List<misc> getAllMisc(GamePanel gp) {
        List<misc> list = new ArrayList<>();
        list.add(new misc("Coal", gp,  10, 5));
        list.add(new misc("Firewood", gp, 15, 8));
        list.add(new misc("Proposal Ring", gp, 400, 50));
        return list;
    }

    public static misc findByName(String name) {
        for (misc m : miscList) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
}
