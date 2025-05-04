package main;

import object.OBJ_Chest;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }

    public void setObject(){

        gp.obj[0] =  new OBJ_Chest();
        gp.obj[0].worldX = 4 * gp.tileSize;
        gp.obj[0].worldY = 45 * gp.tileSize;
        // gp.obj[0].collisionOn = true;

        // gp.obj[1] =  new OBJ_Chest();
        // gp.obj[1].worldX = 42 * gp.tileSize;
        // gp.obj[1].worldY = 4 * gp.tileSize;
        

    }
}