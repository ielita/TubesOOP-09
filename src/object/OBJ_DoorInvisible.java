package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_DoorInvisible extends SuperObject {
    private GamePanel gp;
    public String message = "";
    private String destinationMap;
    private int destinationX;
    private int destinationY;
    private boolean hasTriggered = false;

    public OBJ_DoorInvisible(GamePanel gp, String destMap, int destX, int destY) {
        this.gp = gp;
        this.destinationMap = destMap;
        this.destinationX = destX;
        this.destinationY = destY;
        name = "DoorInvisible";

        try {
            image = ImageIO.read(new File(".png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        
        collision = false;
        
        
        triggerArea = new Rectangle(
            0,    
            0,    
            gp.tileSize/2, 
            gp.tileSize/2 
        );
        
        
        solidArea = new Rectangle(0, 0, 0, 0);
    }

    
    public void update() {
        
        if (!hasTriggered && isPlayerInRange(gp,64)) {
            
            message = "Going to " + destinationMap + "...";
            gp.tileM.mapManager.changeMap(destinationMap, destinationX, destinationY);
            hasTriggered = true;
        }
    }

}