package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp,KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
    }

    public void setDefultValues(){

        x = 200;
        y = 200;
        speed = 5;
    }
}