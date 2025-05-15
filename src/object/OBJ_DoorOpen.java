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
            gp.tileSize, 
            gp.tileSize 
        );
        
        // Set solidArea to zero size to prevent any collision
        solidArea = new Rectangle(0, 0, 0, 0);
    }

    
    public void update() {
        // Check if player is in trigger area
        if (!hasTriggered && isPlayerNearby(gp)) {
            // Instantly change map when player touches trigger area
            message = "Going to " + destinationMap + "...";
            gp.tileM.mapManager.changeMap(destinationMap, destinationX, destinationY);
            hasTriggered = true;
    }
    }

    private boolean isPlayerNearby(GamePanel gp) {
        Rectangle playerBounds = new Rectangle(
            gp.player.worldX + gp.player.solidArea.x,
            gp.player.worldY + gp.player.solidArea.y,
            gp.player.solidArea.width,
            gp.player.solidArea.height
        );

        Rectangle doorTrigger = new Rectangle(
            worldX + triggerArea.x,
            worldY + triggerArea.y,
            triggerArea.width,
            triggerArea.height
        );

        return playerBounds.intersects(doorTrigger);
    }


}
