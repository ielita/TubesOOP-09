package object;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;


public class OBJ_Chest extends SuperObject{
    
    public OBJ_Chest() {

        name = "Chest";

        try{
            image = ImageIO.read(new File("res/objects/chest.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}