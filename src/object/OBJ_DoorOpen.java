package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_DoorOpen extends SuperObject {
    private GamePanel gp;
    public String message = "";
    private String destinationMap;
    private int destinationX;
    private int destinationY;
    private boolean hasTriggered = false;
    private Rectangle triggerArea;

    public OBJ_DoorOpen(GamePanel gp, String destMap, int destX, int destY) {
        this.gp = gp;
        this.destinationMap = destMap;
        this.destinationX = destX;
        this.destinationY = destY;
        name = "DoorOpen";

        try {
            image = ImageIO.read(new File("res/objects/Door22.png"));
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
            if (!gp.tileM.mapManager.getCurrentMap().equals("insideHouse")){
            gp.player.setEnergy(gp.player.getEnergy()-10);
            gp.timeM.setMinute(gp.timeM.getMinute() + 15);
            }
            hasTriggered = true;
    }
    }

}