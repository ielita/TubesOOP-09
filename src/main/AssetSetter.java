package main;

// import main.GamePanel;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_DoorOpen;

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
            // Regular door
            gp.obj[0] = new OBJ_Door(gp, "insideHouse", 2, 3);
            gp.obj[0].worldX = 3 * gp.tileSize;
            gp.obj[0].worldY = 6 * gp.tileSize;

            // Auto-transition door
            gp.obj[1] = new OBJ_DoorOpen(gp, "town", 2, 3);
            gp.obj[1].worldX = 30 * gp.tileSize;
            gp.obj[1].worldY = 15 * gp.tileSize;
            gp.obj[2] = new OBJ_DoorOpen(gp, "town", 2, 4);
            gp.obj[2].worldX = 30 * gp.tileSize;
            gp.obj[2].worldY = 16 * gp.tileSize;
        }

        else if (mapName.equals("town")) {
            // Regular door

            gp.obj[0] = new OBJ_DoorOpen(gp, "farm", 28, 15);
            gp.obj[0].worldX = 0 * gp.tileSize;
            gp.obj[0].worldY = 3 * gp.tileSize;

            gp.obj[1] = new OBJ_DoorOpen(gp, "farm", 28, 16);
            gp.obj[1].worldX = 0 * gp.tileSize;
            gp.obj[1].worldY = 4 * gp.tileSize;
        }

    }

    public void setNPC(){

        // gp.npc[0] =  new NPC_clone(gp);
        // gp.npc[0].worldX = 4 * gp.tileSize;
        // gp.npc[0].worldY = 4 * gp.tileSize;


    }

}
