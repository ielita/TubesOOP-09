package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.CollisionChecker;
import main.GamePanel;
import main.KeyHandler;

public class OBJ_Door extends SuperObject {
    private CollisionChecker cChecker;
    private KeyHandler keyH;
    private GamePanel gp;
    public String message = "";
    private String destinationMap;
    private int destinationX;
    private int destinationY;

    public OBJ_Door(GamePanel gp, String destMap, int destX, int destY) {
        this.gp = gp;
        this.keyH = gp.keyH;
        this.cChecker = gp.cChecker;
        this.destinationMap = destMap;
        this.destinationX = destX;
        this.destinationY = destY;
        name = "Door";

        try {
            image = ImageIO.read(new File("res/objects/Door1.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
        solidArea = new Rectangle(0, 0, 48, 48);
    }

    @Override
    protected void onInteract() {
        if (collision) {
            message = "Going to " + destinationMap + "...";
            gp.tileM.mapManager.changeMap(destinationMap, destinationX, destinationY);
            collision = false;
        }
    }

    public String getMessage() {
        return message;
    }
}
