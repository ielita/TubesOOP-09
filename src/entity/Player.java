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
import main.UtilityTool;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp,KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 19;   
        solidArea.y = 25;
        solidArea.width = 25;
        solidArea.height = 25;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 41;
        worldY = gp.tileSize * 40;
        speed = 5;
        direction = "down";
    }

    public void getImage() {

        up1 = setup("player/player_up_1");
        up2 = setup("player/player_up_2");
        down1 = setup("player/player_down_1");
        down2 = setup("player/player_down_2");
        left1 = setup("player/player_left_1");
        left2 = setup("player/player_left_2");
        right1 = setup("player/player_right_1");
        right2 = setup("player/player_right_2");
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

        g2.drawImage(image, screenX, screenY, null);


        // COLLISION AREA
        // g2.setColor(Color.YELLOW);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}