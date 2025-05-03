
package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    // Screen settings
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 16;    // horizontal 
    public final int maxScreenRow = 12;    // vertikal 
    public final int screenWidth = tileSize * maxScreenCol;    
    public final int screenHeight = tileSize * maxScreenRow;   
    
    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    Player player = new Player(this,keyH);



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

            double drawInterval = 1000000000/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

        while(gameThread != null){

            
            currentTime = System.nanoTime();

            delta += (currentTime-lastTime)/drawInterval;

            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta --;
                
            }
        }

    }

    public void update(){
        
        player.update();

    }

    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        player.draw(g2);
        g2.dispose(); 
    }
}