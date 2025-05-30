package main;

import java.util.List;

import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(String mapName) {
        for(int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        if (mapName.equals("insideHouse")) {
            gp.obj[0] = new OBJ_Oven(gp);
            gp.obj[0].worldX = 2 * gp.tileSize;
            gp.obj[0].worldY = 1 * gp.tileSize;
            ((OBJ_Oven)gp.obj[0]).setCoalUseCount(gp.player.getCoalUseCount());

            gp.obj[1] = new OBJ_Door(gp, "farm", 3, 8);
            
            gp.obj[1] = new OBJ_Door(gp, "farm", 4, 8);
            gp.obj[1].worldX = 12 * gp.tileSize;
            gp.obj[1].worldY = 23 * gp.tileSize;

            gp.obj[2] = new OBJ_Bed1(gp);
            gp.obj[2].worldX = 4 * gp.tileSize;
            gp.obj[2].worldY = 3 * gp.tileSize;

            gp.obj[3] = new OBJ_Bed2(gp);
            gp.obj[3].worldX = 4 * gp.tileSize;
            gp.obj[3].worldY = 4 * gp.tileSize;
        }

        else if (mapName.equals("farm")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "insideHouse", 12, 22);
            gp.obj[0].worldX = (int)(4.5 * gp.tileSize);
            gp.obj[0].worldY = (int)(6.3 * gp.tileSize);

            gp.obj[1] = new OBJ_DoorOpen(gp, "town", 3, 21);
            gp.obj[1].worldX = 31 * gp.tileSize;
            gp.obj[1].worldY = 15 * gp.tileSize;

            gp.obj[2] = new OBJ_DoorOpen(gp, "town", 3, 22);
            gp.obj[2].worldX = 31 * gp.tileSize;
            gp.obj[2].worldY = 16 * gp.tileSize;

            gp.obj[3] = new OBJ_ShippingBin(gp);
            gp.obj[3].worldX = 10 * gp.tileSize;
            gp.obj[3].worldY = 7 * gp.tileSize;
        }

        else if (mapName.equals("town")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "farm", 27, 15);
            gp.obj[0].worldX = 0 * gp.tileSize;
            gp.obj[0].worldY = 21 * gp.tileSize;

            gp.obj[1] = new OBJ_DoorOpen(gp, "farm", 27, 16);
            gp.obj[1].worldX = 0 * gp.tileSize;
            gp.obj[1].worldY = 22 * gp.tileSize;

            gp.obj[2] = new OBJ_DoorOpen(gp, "mountainlake", 7, 13);
            gp.obj[2].worldX = 24 * gp.tileSize;
            gp.obj[2].worldY = 1 * gp.tileSize;

            gp.obj[3] = new OBJ_DoorOpen(gp, "forestriver", 2, 5);
            gp.obj[3].worldX = 46 * gp.tileSize;
            gp.obj[3].worldY = 21 * gp.tileSize;

            gp.obj[4] = new OBJ_DoorOpen(gp, "ocean", 7, 3);
            gp.obj[4].worldX = 24 * gp.tileSize;
            gp.obj[4].worldY = 49 * gp.tileSize;
        }

        else if (mapName.equals("mountainlake")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 3);
            gp.obj[0].worldX = 7 * gp.tileSize;
            gp.obj[0].worldY = 15 * gp.tileSize;
        }

        else if (mapName.equals("forestriver")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 44, 21);
            gp.obj[0].worldX = 1 * gp.tileSize;
            gp.obj[0].worldY = 5 * gp.tileSize;

            gp.obj[1] = new OBJ_DoorOpen(gp, "town", 44, 22);
            gp.obj[1].worldX = 1 * gp.tileSize;
            gp.obj[1].worldY = 6 * gp.tileSize;
        }

        else if (mapName.equals("ocean")){
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 47);
            gp.obj[0].worldX = 7 * gp.tileSize;
            gp.obj[0].worldY = 1 * gp.tileSize;
        }
    }
}
