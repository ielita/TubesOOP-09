package main;

// import main.GamePanel;
import object.OBJ_Chest;
import entity.NPC_clone;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }

    public void setObject(){

        gp.obj[0] =  new OBJ_Chest(gp);
        gp.obj[0].worldX = 4 * gp.tileSize;
        gp.obj[0].worldY = 41 * gp.tileSize;


    }

    public void setNPC(){

        gp.npc[0] =  new NPC_clone(gp);
        gp.npc[0].worldX = 41 * gp.tileSize;
        gp.npc[0].worldY = 39 * gp.tileSize;


    }
}