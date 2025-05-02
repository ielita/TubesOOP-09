
package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    // Screen settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;    // horizontal 
    final int maxScreenRow = 12;    // vertikal 
    final int screenWidth = tileSize * maxScreenCol;    // 768 pixel
    final int screenHeight = tileSize * maxScreenRow;   // 576 pixel
    
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        while(gameThread != null){
            System.out.println("Game is looping");
        }

    }

    public void update(){

        update();

        repaint();

    }

    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);

        g2.fillRect(100,100,tileSize,tileSize);
        g2.dispose(); 
    }
}