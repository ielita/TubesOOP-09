package object;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Door extends SuperObject {
    
    public OBJ_Door(GamePanel gp) {

        name = "Door";

        try {
            image = ImageIO.read(new File("res/objects/Door1.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            System.err.println("Failed to load door image: " + e.getMessage());
        }

        collision = true;
    }
}
