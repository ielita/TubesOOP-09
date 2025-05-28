package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Bed2 extends SuperObject {

    private GamePanel gp;
    public String message = "";

    public OBJ_Bed2(GamePanel gp) {
        this.gp = gp;
        this.keyH = gp.keyH;
        name = "Bed1";

        try {
            image = ImageIO.read(new File("res/objects/Bed2.png"));
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

    }

    public String getMessage() {
        return message;
    }
}
