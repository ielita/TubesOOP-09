package object;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import main.GamePanel;

public class OBJ_Chest extends SuperObject{
    
    public OBJ_Chest(GamePanel gp) {

        name = "Chest";

        try{
            image = ImageIO.read(new File("res/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize,gp.tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}