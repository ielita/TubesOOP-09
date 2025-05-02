package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp,KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        setDefultValues();
    }

    public void setDefultValues(){

        x = 200;
        y = 200;
        speed = 5;
    }

    public void update(){


        if (keyH.upPressed == true){
            y-=speed;
        }
        else if (keyH.downPressed == true){
            y+=speed;
        }
        else if (keyH.leftPressed == true){
            x-=speed;
        }
        else if (keyH.rightPressed == true){
            x+=speed;
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}