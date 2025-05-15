package main;

// import main.GamePanel;
import entity.NPC_clone;
import object.OBJ_Chest;
import object.OBJ_Door;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }

    public void setObject(){

        gp.obj[0] =  new OBJ_Chest(gp);
        gp.obj[0].worldX = 1 * gp.tileSize;
        gp.obj[0].worldY = 1 * gp.tileSize;


    }

    public void setNPC(){

        gp.npc[0] =  new NPC_clone(gp);
        gp.npc[0].worldX = 4 * gp.tileSize;
        gp.npc[0].worldY = 4 * gp.tileSize;


    }

    public void setDoor(){

        gp.obj[0] =  new OBJ_Door(gp);
        gp.obj[0].worldX = 2 * gp.tileSize;
        gp.obj[0].worldY = 1 * gp.tileSize;
}
}
