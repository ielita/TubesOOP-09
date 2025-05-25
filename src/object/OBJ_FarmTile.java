package object;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import items.seed;
import items.crop;
import main.TimeManager;

public class OBJ_FarmTile extends SuperObject {
    private BufferedImage tilted;      // Basic tilled state
    private BufferedImage tiltedW;     // Tilled and watered
    private BufferedImage planted;     // Has planted seed
    private BufferedImage plantedW;    // Planted and watered
    private BufferedImage harvest;     // Ready to harvest
    
    private boolean tilled = false;
    private boolean watered = false;
    private seed plantedSeed = null;
    private int daysUntilHarvest = 0;

    public OBJ_FarmTile(GamePanel gp) {
        this.gp = gp;
        name = "Farm Tile";
        loadSprites();
        image = tilted; // Default sprite
    }

    private void loadSprites() {
        try {
            tilted = ImageIO.read(getClass().getResourceAsStream("/tiles/TILT_ED.png"));
            tiltedW = ImageIO.read(getClass().getResourceAsStream("/tiles/TILT_ED_W.png"));
            planted = ImageIO.read(getClass().getResourceAsStream("/tiles/PLAN_TED.png"));
            plantedW = ImageIO.read(getClass().getResourceAsStream("/tiles/PLAN_TED_W.png"));
            harvest = ImageIO.read(getClass().getResourceAsStream("/tiles/HAR_VEST.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSprite() {
        if (!tilled) {
            image = tilted;
        } else if (plantedSeed == null) {
            image = watered ? tiltedW : tilted;
        } else if (daysUntilHarvest <= 0) {
            image = harvest;
        } else {
            image = watered ? plantedW : planted;
        }
    }

    @Override
    protected void onInteract() {
        if (gp.player.getOnhandItem() != null) {
            // If holding hoe, till the ground
            if (gp.player.getOnhandItem().getName().equals("Hoe")) {
                till();
            }
            // If holding watering can, water the tile
            else if (gp.player.getOnhandItem().getName().equals("Watering Can")) {
                water();
            }
            // If holding seed, plant it
            else if (gp.player.getOnhandItem() instanceof seed && tilled) {
                plant((seed)gp.player.getOnhandItem());
            }
        }
        // If tile has mature crop and no item held, harvest
        else if (isReadyToHarvest()) {
            harvest();
        }
        updateSprite();
    }

    private void till() {
        if (!tilled) {
            tilled = true;
            updateSprite();
        }
    }

    private void water() {
        if (tilled) {
            watered = true;
            updateSprite();
        }
    }

    private void plant(seed seed) {
        if (tilled && plantedSeed == null) {
            plantedSeed = seed;
            daysUntilHarvest = seed.getGrowthTime();
            updateSprite();
        }
    }

    private void harvest() {
        if (isReadyToHarvest()) {
            // Create harvested crop and add to inventory
            crop harvestedCrop = new crop(plantedSeed.getCropType());
            gp.player.addItemToInventory(harvestedCrop, 1);
            
            // Reset tile
            plantedSeed = null;
            tilled = false;
            watered = false;
            try {
                image = ImageIO.read(new File("res/tiles/untilled.png"));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGrowth() {
        if (plantedSeed != null && watered) {
            daysUntilHarvest--;
            updateSprite();
            watered = false; // Need to water again next day
        }
    }

    private boolean isReadyToHarvest() {
        return plantedSeed != null && daysUntilHarvest <= 0;
    }

    @Override
    public void update() {
        // This will be called by the game loop to update growth state
        if (gp.TimeManager.isNewDay()) {
            updateGrowth();
        }
    }
}