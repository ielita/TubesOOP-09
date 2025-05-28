package main;
import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import object.SuperObject;
import tile.MapManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

    // Screen settings
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 15;    // horizontal 
    public final int maxScreenRow = 9;    // vertikal 
    public final int screenWidth = tileSize * maxScreenCol;    
    public final int screenHeight = tileSize * maxScreenRow;   
    
    //WORLD SETTINGS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FOR FULL SCREEN
    int screenHeight2 = screenHeight;
    int screenWidth2 = screenWidth;

    BufferedImage tempScreen; 
    Graphics2D g2;


// Default starting map

    //FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public MapManager mapM = new MapManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    public UI ui = new UI(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    // ENTITY AND OBJECT
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this,keyH);
    public TimeManager timeM = new TimeManager(this);

    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int optionsState = 2;
    public final int inventoryState = 3;
    public final int interacingState = 4;
    public final int chattingState = 5;
    public final int givingGiftState = 6;
    public String currentMap = mapM.getCurrentMap(); 
    public boolean fullScreenOn = false; 
    public boolean backsoundOn = true; 
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setNPC(mapM.currentMap);
        aSetter.setObject(mapM.currentMap);
        gameState = menuState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();

    }

public void setFullScreen() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();

    if (fullScreenOn) {

        Main.window.dispose(); 
        Main.window.setUndecorated(true);
        Main.window.setResizable(false);
        Main.window.setVisible(true);

        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    } else {
        gd.setFullScreenWindow(null); 

        Main.window.dispose(); 
        Main.window.setUndecorated(false);
        Main.window.setResizable(true);
        Main.window.setVisible(true);

        Main.window.setSize(screenWidth, screenHeight);
        Main.window.setLocationRelativeTo(null); 

        screenWidth2 = screenWidth;
        screenHeight2 = screenHeight;
    }
}


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

            double drawInterval = 1000000000.0/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime-lastTime)/drawInterval;

            lastTime = currentTime;

            if (delta >= 1){
                update();
                drawToTempScreen();
                drawToScreen();
                // repaint();
                delta --;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            timeM.update(); // Update game time
            player.update();
            
            // Update current location from MapManager
            currentMap = mapM.getCurrentMap();
            
            // Update all objects
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].update();
                }
            }
        }
        
        if (timeM.isNewDay()) {
            tileM.mapManager.updatePlantGrowth();
        }
    }

    public void drawToTempScreen() {
        // Clear the temp screen
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);
        
        // Draw tile manager
        tileM.draw(g2);
        
        // Draw objects
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        
        // Draw NPCs
        for(int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }
        
        // Draw player
        player.draw(g2);
        
        // Draw UI
        ui.draw(g2);
    }

   


    public void drawToScreen(){
        
        Graphics g = this.getGraphics();
        
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        
        g.dispose();
    }

    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();
        backsoundOn = true;
    }

    public void stopMusic(){
        
        music.stop();
        backsoundOn = false;
    }

    public void playSE(int i){

        soundEffect.setFile(i);
        soundEffect.play();
    }

    // Update this whenever loading a new map
}