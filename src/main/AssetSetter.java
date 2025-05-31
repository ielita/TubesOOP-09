package main;

import java.util.List;

import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(String mapName) {
        String selectedFarm = gp.tileM.mapManager.getSelectedFarmMap();
        for(int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        if (mapName.equals("insideHouse")) {
            gp.obj[0] = new OBJ_Oven(gp);
            gp.obj[0].worldX = 2 * gp.tileSize;
            gp.obj[0].worldY = 1 * gp.tileSize;
            ((OBJ_Oven)gp.obj[0]).setCoalUseCount(gp.player.getCoalUseCount());

            if (selectedFarm.equals("farm")) {
                gp.obj[1] = new OBJ_Door(gp, "farm", 4, 8);
            } else if (selectedFarm.equals("farm1")) {
                gp.obj[1] = new OBJ_Door(gp, "farm1", 4, 29);
            } else if (selectedFarm.equals("farm2")) {
                gp.obj[1] = new OBJ_Door(gp, "farm2", 26, 8);
            } else if (selectedFarm.equals("farm3")) {
                gp.obj[1] = new OBJ_Door(gp, "farm3", 5,18);
            } 
            gp.obj[1].worldX = 12 * gp.tileSize;
            gp.obj[1].worldY = 23 * gp.tileSize;

            gp.obj[2] = new OBJ_Bed1(gp);
            gp.obj[2].worldX = 4 * gp.tileSize;
            gp.obj[2].worldY = 3 * gp.tileSize;

            gp.obj[3] = new OBJ_Bed2(gp);
            gp.obj[3].worldX = 4 * gp.tileSize;
            gp.obj[3].worldY = 4 * gp.tileSize;
        }

        else if (mapName.equals("farm") || mapName.startsWith("farm")) {
            setFarmObjects(mapName);
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
            gp.obj[2].worldY = 0 * gp.tileSize;

            gp.obj[3] = new OBJ_DoorOpen(gp, "forestriver", 2, 5);
            gp.obj[3].worldX = 47 * gp.tileSize;
            gp.obj[3].worldY = 21 * gp.tileSize;

            gp.obj[4] = new OBJ_DoorOpen(gp, "ocean", 7, 1);
            gp.obj[4].worldX = 24 * gp.tileSize;
            gp.obj[4].worldY = 50 * gp.tileSize;

            gp.obj[5] = new OBJ_Door(gp, "store", 6, 9);
            gp.obj[5].worldX = (int)(41.5 * gp.tileSize);
            gp.obj[5].worldY = (int)(7.3 * gp.tileSize);

            List<OBJ_NPC> npcs = gp.player.allNPCs;
            int temp = 14;
            for (int i = 1; i < npcs.size(); i++) {
                gp.obj[i + 6] = npcs.get(i);
                gp.obj[i + 6].worldX = temp * gp.tileSize;
                gp.obj[i + 6].worldY = 25 * gp.tileSize;
                temp += 3;
            }
        }

        else if (mapName.equals("mountainlake")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 1);
            gp.obj[0].worldX = 7 * gp.tileSize;
            gp.obj[0].worldY = 15 * gp.tileSize;
        }

        else if (mapName.equals("forestriver")) {
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 44, 21);
            gp.obj[0].worldX = 0 * gp.tileSize;
            gp.obj[0].worldY = 5 * gp.tileSize;

        }

        else if (mapName.equals("ocean")){
            gp.obj[0] = new OBJ_DoorOpen(gp, "town", 24, 47);
            gp.obj[0].worldX = 7 * gp.tileSize;
            gp.obj[0].worldY = 0 * gp.tileSize;
        }

        else if (mapName.equals("store")) {
            gp.obj[0] = new OBJ_Door(gp, "town", 41, 9);
            gp.obj[0].worldX = (int)(6 * gp.tileSize);
            gp.obj[0].worldY = (int)(11 * gp.tileSize);
            List<OBJ_NPC> npcs = gp.player.allNPCs;
            gp.obj[1] = npcs.get(0);
            gp.obj[1].worldX = 6 * gp.tileSize;
            gp.obj[1].worldY = 3 * gp.tileSize;

        }
    }
    
    private void setFarmObjects(String farmMapName) {
        
        String selectedFarm = gp.tileM.mapManager.getSelectedFarmMap();

        gp.obj[0] = new OBJ_Door(gp, "insideHouse", 12, 20);

        gp.obj[1] = new OBJ_DoorOpen(gp, "town", 3, 21);
        gp.obj[2] = new OBJ_DoorOpen(gp, "town", 3, 22);

        gp.obj[3] = new OBJ_ShippingBin(gp);


        switch (selectedFarm) {
            case "farm": 
                setHouseDoorPosition(4, 6);
                setShippingBinPosition(11, 5);
                break;

            case "farm1": 
                setHouseDoorPosition(4, 27);
                setShippingBinPosition(11, 26);
                break;

            case "farm2": 

                setHouseDoorPosition(25, 6);
                setShippingBinPosition(21, 5); 
                break;

            case "farm3": 
                setHouseDoorPosition(4, 16);
                setShippingBinPosition(10, 14);
                break;

            default: 
                setHouseDoorPosition(4, 6);
                setShippingBinPosition(11, 5);
                break;
        }


        gp.obj[1].worldX = 31 * gp.tileSize;
        gp.obj[1].worldY = 15 * gp.tileSize;
        gp.obj[2].worldX = 31 * gp.tileSize;
        gp.obj[2].worldY = 16 * gp.tileSize;
    }

    private void setHouseDoorPosition(int x, int y) {
        gp.obj[0].worldX = (int)((x + 0.5) * gp.tileSize);
        gp.obj[0].worldY = (int)((y + 0.3) * gp.tileSize);
    }

    private void setShippingBinPosition(int x, int y) {
        gp.obj[3].worldX = x * gp.tileSize;
        gp.obj[3].worldY = y * gp.tileSize;
    }
}