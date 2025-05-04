package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;


public class UI{

    GamePanel gp;
    Font arial_40;

    public UI (GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2){
        
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString(" SPAKBOR SI PETANI", 20, 50);
    }
}