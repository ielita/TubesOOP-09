package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_ShippingBin extends SuperObject {

    private GamePanel gp;
    public String message = "";
    public static int goldEarned = 0; 

    public OBJ_ShippingBin(GamePanel gp) {
        this.gp = gp;
        this.keyH = gp.keyH;
        name = "ShippingBin"; 
        try {
            image = ImageIO.read(new File(""));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
        solidArea = new Rectangle(0, 0, 48, 48);
    }

    @Override
    public void update() {
        
        
    }

    @Override
    protected void onInteract() {
        if (collision) {
            message = "Opening shipping bin...";
            gp.gameState = gp.shippingBinState;
            gp.keyH.cursorIndex = 0; 
            gp.player.setOnhandItem(null);
            return;
        }
    }

    public String getMessage() {
        return message;
    }
}