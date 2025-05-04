package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp,KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 19;
        solidArea.y = 25;
        solidArea.width = 25;
        solidArea.height = 25;

        setDefultValues();
        getPlayerImage();
    }

    public void setDefultValues(){

        worldX = gp.tileSize * 13;
        worldY = gp.tileSize * 11;
        speed = 5;
        direction = "down";
    }

public void getPlayerImage() {
    try {
        // Use File() with a relative path to load images
        up1 = ImageIO.read(new File("res/player/player_up_1.png"));
        up2 = ImageIO.read(new File("res/player/player_up_2.png"));
        down1 = ImageIO.read(new File("res/player/player_down_1.png"));
        down2 = ImageIO.read(new File("res/player/player_down_2.png"));
        left1 = ImageIO.read(new File("res/player/player_left_1.png"));
        left2 = ImageIO.read(new File("res/player/player_left_2.png"));
        right1 = ImageIO.read(new File("res/player/player_right_1.png"));
        right2 = ImageIO.read(new File("res/player/player_right_2.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void update(){

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true ){

            if (keyH.upPressed == true){
                
                direction = "up";
            }
            else if (keyH.downPressed == true){
                
                direction = "down";
            }
            else if (keyH.leftPressed == true){
                
                direction = "left";
            }
            else if (keyH.rightPressed == true){
                
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);


            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch(direction){
                    case "up":
                        worldY-=speed;
                        break;
                    case "down":
                        worldY+=speed;
                        break;
                    case "left":
                        worldX-=speed;
                        break;
                    case "right":
                        worldX+=speed;
                        break;
                }
            }


            spriteCounter++;
            if (spriteCounter > 11){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);

        // g2.fillRect(worldX, worldY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}