package object;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;


public class OBJ_Sword extends SuperObject{
    
    public OBJ_Sword() {

        name = "Sword";

        try{
            image = ImageIO.read(new File("res/objects/kub1.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}