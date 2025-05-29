package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Bed1 extends SuperObject {

    private GamePanel gp;
    public String message = "";

    public OBJ_Bed1(GamePanel gp) {
        this.gp = gp;
        this.keyH = gp.keyH;
        name = "Bed1";

        try {
            image = ImageIO.read(new File("res/objects/Bed1.png"));
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
            message = "Going to bed...";

            gp.tileM.mapManager.changeMap("insideHouse", 3, 3);
            collision = false;
            gp.player.setEnergy(100);
            gp.timeM.skipDay();



            // Call the new sleep method from player
            gp.player.sleep();

            gp.keyH.interactPressed = false;
        }
    }

    public String getMessage() {
        return message;
    }
}
