package object;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_FarmTile extends SuperObject{
    
    public OBJ_FarmTile(GamePanel gp) {
        
        name = "FarmTile";

        try{
            image = ImageIO.read(new File("res/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize,gp.tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}