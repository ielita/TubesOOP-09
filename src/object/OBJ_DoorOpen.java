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

        // Completely disable collision
        collision = false;
        
        // Only use trigger area for map change
        triggerArea = new Rectangle(
            0,    
            0,    
            gp.tileSize/2, 
            gp.tileSize/2 
        );
        
        // Set solidArea to zero size to prevent any collision
        solidArea = new Rectangle(0, 0, 0, 0);
    }

    
    public void update() {
        // Check if player is in trigger area
        if (!hasTriggered && isPlayerInRange(gp,64)) {
            // Instantly change map when player touches trigger area
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