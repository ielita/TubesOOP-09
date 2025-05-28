package main;

// import main.GamePanel;
import object.*;

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

            gp.obj[2] = new OBJ_Bed1(gp);
            gp.obj[2].worldX = 4 * gp.tileSize;
            gp.obj[2].worldY = 3 * gp.tileSize;

            gp.obj[3] = new OBJ_Bed2(gp);
            gp.obj[3].worldX = 4 * gp.tileSize;
            gp.obj[3].worldY = 4 * gp.tileSize;

        }

        else if (mapName.equals("farm")) {
            // Regular door
            gp.obj[0] = new OBJ_DoorOpen(gp, "insideHouse", 2, 3);
            gp.obj[0].worldX = 3 * gp.tileSize;
            gp.obj[0].worldY = 6 * gp.tileSize;

            // Auto-transition door
            gp.obj[1] = new OBJ_DoorOpen(gp, "town", 3, 21);
            gp.obj[1].worldX = 31 * gp.tileSize;
            gp.obj[1].worldY = 15 * gp.tileSize;
            gp.obj[2] = new OBJ_DoorOpen(gp, "town", 3, 22);
            gp.obj[2].worldX = 31 * gp.tileSize;
            gp.obj[2].worldY = 16 * gp.tileSize;
        }

        else if (mapName.equals("town")) {
            // Regular door

            gp.obj[0] = new OBJ_DoorOpen(gp, "farm", 27, 15);
            gp.obj[0].worldX = 0 * gp.tileSize;
            gp.obj[0].worldY = 21 * gp.tileSize;

            gp.obj[1] = new OBJ_DoorOpen(gp, "farm", 27, 16);
            gp.obj[1].worldX = 0 * gp.tileSize;
            gp.obj[1].worldY = 22 * gp.tileSize;
            
        // Top middle door (around lake area)
            gp.obj[2] = new OBJ_DoorOpen(gp, "mountainlake", 7, 13);
            gp.obj[2].worldX = 24 * gp.tileSize;  // Center of map width
            gp.obj[2].worldY = 1 * gp.tileSize;   

            gp.obj[3] = new OBJ_DoorOpen(gp, "forestriver", 2, 5);
            gp.obj[3].worldX = 46 * gp.tileSize;  // Right wall
            gp.obj[3].worldY = 21 * gp.tileSize;  // Middle of right wall

            gp.obj[4] = new OBJ_DoorOpen(gp, "forestriver", 2, 6);
            gp.obj[4].worldX = 46 * gp.tileSize;  // Right wall
            gp.obj[4].worldY = 22 * gp.tileSize;  // Middle of right wall

            gp.obj[4] = new OBJ_DoorOpen(gp, "ocean", 7, 3);
            gp.obj[4].worldX = 24 * gp.tileSize;  // Center of map width
            gp.obj[4].worldY = 49 * gp.tileSize;  // Bottom wall
    }
        else if (mapName.equals("mountainlake")) {
            // Regular door
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 3);
            gp.obj[0].worldX = 7* gp.tileSize;  // Center of map width
            gp.obj[0].worldY = 15* gp.tileSize;  // Bottom wall
        }

        else if (mapName.equals("forestriver")) {
            // Regular door
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 44, 21);
            gp.obj[0].worldX = 1 * gp.tileSize;  
            gp.obj[0].worldY = 5* gp.tileSize;  

            gp.obj[1] = new OBJ_DoorOpen(gp, "town", 44, 22);
            gp.obj[1].worldX = 1 * gp.tileSize;  
            gp.obj[1].worldY = 6* gp.tileSize; 
        }

        else if (mapName.equals("ocean")){
            // Regular door
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 47);
            gp.obj[0].worldX = 7* gp.tileSize;  // Center of map width
            gp.obj[0].worldY = 1* gp.tileSize;  // Bottom wall
        }


        // Add more maps and objects as needed

    }

    

    public void setNPC(){

        // gp.npc[0] =  new NPC_clone(gp);
        // gp.npc[0].worldX = 4 * gp.tileSize;
        // gp.npc[0].worldY = 4 * gp.tileSize;


    }

}
