package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.InventoryManager;

public class OBJ_ShippingBin extends SuperObject {

    private GamePanel gp;
    public String message = "";
    private InventoryManager inventory;
    public static int goldEarned = 0; // Keep this to accumulate gold

    public OBJ_ShippingBin(GamePanel gp) {
        this.gp = gp;
        this.keyH = gp.keyH;
        name = "ShippingBin"; // Fix name
        this.inventory = gp.player.inventoryManager;
        

        try {
            image = ImageIO.read(new File("res/objects/shippingbin.png"));
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
        // Remove all gold processing logic from here
        // This now does nothing or handle other shipping bin updates
    }

    @Override
    protected void onInteract() {
        if (collision) {
            message = "Opening shipping bin...";
            gp.gameState = gp.shippingBinState;
            gp.keyH.inventoryCursorIndex = 0; 
            gp.player.setOnhandItem(null);
            return;
        }
    }

    public String getMessage() {
        return message;
    }
}
