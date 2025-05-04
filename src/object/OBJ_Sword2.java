package object;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;


public class OBJ_Sword2 extends SuperObject{
    
    public OBJ_Sword2() {

        name = "Sword";

        try{
            image = ImageIO.read(new File("res/objects/kub2.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}