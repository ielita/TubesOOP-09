package main;
import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    public TimeManager timeManager = new TimeManager(this); // Changed variable name for clarity

    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public void setupGame(){

        
        aSetter.setNPC();
        aSetter.setObject(mapM.currentMap);
        gameState = menuState;
        

=======
    public void setupGame() {
        aSetter.setNPC();
        aSetter.setObject(mapM.currentMap);
        gameState = menuState;
        // Initialize time system
        timeManager.setTime(6, 0); // Start at 6:00 AM
>>>>>>> Stashed changes
=======
    public void setupGame() {
        aSetter.setNPC();
        aSetter.setObject(mapM.currentMap);
        gameState = menuState;
        // Initialize time system
        timeManager.setTime(6, 0); // Start at 6:00 AM
>>>>>>> Stashed changes
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

    public void update() {
        if(gameState == playState) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            timeM.update(); // Update game time
            player.update();
=======
=======
>>>>>>> Stashed changes
            // Update time first
            timeManager.update();
            
            // Then update everything that depends on time
            player.update();
            currentMap = mapM.getCurrentMap();
            
>>>>>>> Stashed changes
            // Update all objects
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].update();
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // TILE
        tileM.draw(g2);

<<<<<<< Updated upstream
<<<<<<< Updated upstream
        

        //OBJECT
        for(int i = 0; i < obj.length; i++){
            if (obj[i] != null){
=======
        // OBJECT
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
>>>>>>> Stashed changes
=======
        // OBJECT
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
>>>>>>> Stashed changes
                obj[i].draw(g2,this);
            }
        }

        // NPC
        for(int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // PLAYER
        player.draw(g2);
<<<<<<< Updated upstream
<<<<<<< Updated upstream


        //UI
        ui.draw(g2);
        
=======
        
        // UI (includes time display)
        ui.draw(g2);
        
        // Draw brightness overlay based on time
        mapM.drawBrightnessOverlay(g2);
>>>>>>> Stashed changes
        
=======
        
        // UI (includes time display)
        ui.draw(g2);
        
        // Draw brightness overlay based on time
        mapM.drawBrightnessOverlay(g2);
        
>>>>>>> Stashed changes
        g2.dispose();
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
}