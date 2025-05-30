package main;
import entity.Entity;
import entity.Player;
import minigame.FishingMiniGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import object.SuperObject;
import object.OBJ_Oven;
import object.OBJ_ShippingBin;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    int screenHeight2 = screenHeight;
    int screenWidth2 = screenWidth;

    BufferedImage tempScreen;
    Graphics2D g2;
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public TimeManager timeM = new TimeManager(this);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
    public Store store;
    public FishingMiniGame fishingMiniGame = new minigame.FishingMiniGame();
    public OBJ_Oven.CookingJob ovenCookingJob = null;
    public String currentMap = tileM.mapManager.getCurrentMap();
    public boolean fullScreenOn = false;
    public boolean backsoundOn = true;
    private boolean autoSleepTriggered = false;

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
    public final int shippingBinState = 11;
    public final int cookingState = 12;
    public final int storeState = 13;
    public final int setupGameInfoState = 14;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
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
            if (!Main.window.isUndecorated()) {
                Main.window.dispose();
                Main.window.setUndecorated(true);
                Main.window.setResizable(false);
                Main.window.setVisible(true);
            }
            gd.setFullScreenWindow(Main.window);
            screenWidth2 = Main.window.getWidth();
            screenHeight2 = Main.window.getHeight();
        } else {
            gd.setFullScreenWindow(null);
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

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            timeM.update();
            player.update();
            if (timeM.isNewDay()) {
                System.out.println("New day detected - updating all plant growth");
                tileM.mapManager.updatePlantGrowth();
                autoSleepTriggered = false;
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].update();
                }
            }
            if (timeM.getTimeString().equals("02:00") && !autoSleepTriggered) {
                if (OBJ_ShippingBin.goldEarned > 0) {
                    int goldFromShipping = object.OBJ_ShippingBin.goldEarned;
                    player.addGold(goldFromShipping);
                    System.out.println("You passed out from exhaustion!");
                    System.out.println("Shipping bin delivered " + goldFromShipping + " gold overnight!");
                    object.OBJ_ShippingBin.goldEarned = 0;
                }
                player.sleep();
                autoSleepTriggered = true;
            }
        }
        if (gameState == sleepState) {
            ui.updateSleepAnimation();
        }
    }

    public void drawToTempScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);
        tileM.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }
        player.draw(g2);
        ui.draw(g2);
        tileM.mapManager.drawBrightnessOverlay(g2);
    }

    public void drawToScreen() {
        Graphics g = this.getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
