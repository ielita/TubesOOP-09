package main;

// import main.GamePanel;
import object.OBJ_Chest;
import object.OBJ_Door;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }

    public void setObject(String mapName) {
        // Clear existing objects first
        for(int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        // Set new objects based on map
        if (mapName.equals("insideHouse")) {
            gp.obj[0] = new OBJ_Chest(gp);
            gp.obj[0].worldX = 1 * gp.tileSize;
            gp.obj[0].worldY = 1 * gp.tileSize;

            gp.obj[1] = new OBJ_Door(gp, "farm", 3, 8);
            gp.obj[1].worldX = 2 * gp.tileSize;
            gp.obj[1].worldY = 5 * gp.tileSize;
        }

        else if (mapName.equals("farm")) {
            gp.obj[0] = new OBJ_Door(gp, "insideHouse", 2, 3);  // Changed from index 2 to 0
            gp.obj[0].worldX = 3 * gp.tileSize;
            gp.obj[0].worldY = 7 * gp.tileSize;
        }
    }

    public void setNPC(){

        // gp.npc[0] =  new NPC_clone(gp);
        // gp.npc[0].worldX = 4 * gp.tileSize;
        // gp.npc[0].worldY = 4 * gp.tileSize;


    }

}
