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
    public final int maxScreenCol = 16;    // horizontal 
    public final int maxScreenRow = 12;    // vertikal 
    public final int screenWidth = tileSize * maxScreenCol;    
    public final int screenHeight = tileSize * maxScreenRow;   
    
    //WORLD SETTINGS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    int screenHeight2 = screenHeight;
    int screenWidth2 = screenWidth;

    BufferedImage tempScreen; 
    Graphics2D g2;

// Default starting map

    //FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    
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
    public final int fishingMiniGameState = 4;
    public final int keyBindingState = 5;
    public final int interactingState = 6;
    public final int chattingState = 7;
    public final int givingGiftState = 8;
    public final int sleepState = 9;
    public final int fishingResultState = 10;
    public minigame.FishingMiniGame fishingMiniGame = new minigame.FishingMiniGame();
    public String currentMap = tileM.mapManager.getCurrentMap(); 
    public boolean fullScreenOn = false; 
    public boolean backsoundOn = true; 

    private boolean autoSleepTriggered = false;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setNPC(tileM.mapManager.currentMap);
        aSetter.setObject(tileM.mapManager.currentMap);
        gameState = menuState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();

    }

    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (fullScreenOn) {
            // Hanya lakukan ini jika belum fullscreen
            if (!Main.window.isUndecorated()) {
                Main.window.dispose();
                Main.window.setUndecorated(true);
                Main.window.setResizable(false);
                Main.window.setVisible(true);
            }
            gd.setFullScreenWindow(Main.window);

            // Ambil ukuran layar sebenarnya
            screenWidth2 = Main.window.getWidth();
            screenHeight2 = Main.window.getHeight();
        } else {
            gd.setFullScreenWindow(null);

            // Hanya lakukan ini jika sudah fullscreen
            if (Main.window.isUndecorated()) {
                Main.window.dispose();
                Main.window.setUndecorated(false);
                Main.window.setResizable(true);
                Main.window.setVisible(true);
            }
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
                drawToScreen();
                repaint();
                // repaint();
                delta --;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            timeM.update(); // Update game time
            timeM.update();
            player.update();

            // Check for new day and update plants
            if (timeM.isNewDay()) {
                System.out.println("New day detected - updating all plant growth");
                tileM.mapManager.updatePlantGrowth();
                // Reset the auto sleep flag for new day
                autoSleepTriggered = false;
            }

            // Update all objects
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].update();
                }
            }

            // Auto sleep at 02:00 - only trigger once
            if(timeM.getTimeString().equals("02:00") && !autoSleepTriggered){
                player.sleep();
                autoSleepTriggered = true; // Prevent multiple calls
            }  
        }

        // Add sleep animation update
        if(gameState == sleepState) {
            ui.updateSleepAnimation();
        }
    }

    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        
        //TILE
        tileM.draw(g2);

        //OBJECT
        for(int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2,this);
            }
        }

        //NPC
        for(int i = 0; i < npc.length; i++){
            if (npc[i] != null){
                npc[i].draw(g2);
            }
        }

        //PLAYER
        player.draw(g2);
        
        ui.draw(g2);
        
        // Draw brightness overlay at the end
        tileM.mapManager.drawBrightnessOverlay(g2);
        
        g2.dispose(); 
    }

    public void drawToScreen() {
        Graphics g = this.getGraphics();
        // Gambar ke ukuran panel yang sebenarnya
        g.drawImage(tempScreen, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }

    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        
        music.stop();
    }

    public void playSE(int i){

        soundEffect.setFile(i);
        soundEffect.play();
    }

    // Update this whenever loading a new map
}