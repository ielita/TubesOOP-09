package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Door extends SuperObject {

    private GamePanel gp;
    public String message = "";
    private String destinationMap;
    private int destinationX;
    private int destinationY;

    public OBJ_Door(GamePanel gp, String destMap, int destX, int destY) {
        this.gp = gp;
        this.keyH = gp.keyH;
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
            if (!gp.tileM.mapManager.getCurrentMap().equals("insideHouse")){
                gp.player.setEnergy(gp.player.getEnergy()-10);
                gp.timeM.setMinute(gp.timeM.getMinute() + 15);
            }
            gp.tileM.mapManager.changeMap(destinationMap, destinationX, destinationY);
            collision = false;
        }
    }

    public String getMessage() {
        return message;
    }
}
