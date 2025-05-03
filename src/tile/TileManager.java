package tile;


import main.GamePanel;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

import java.io.FileInputStream;
import java.io.InputStreamReader;


import java.io.InputStreamReader;

public class TileManager{
    
    GamePanel gp;
    Tile [] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; 

        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        
        try{

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("res/tiles/grass.png"));
        
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("res/tiles/land.png"));
        
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("res/tiles/tree.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("res/tiles/tree2.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("res/tiles/wall.png"));
        
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("res/tiles/wall2.png"));
            
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(new File("res/tiles/water.png"));
        
        }
        catch(IOException e){
            // e.printStackTrace();
            System.out.print("file not found");
            // NOTE TAMBAHIN UNTUK TIAP FILENYA!
        }
        // catch(FileNotFoundException e){
        // }
    }

    public void loadMap(){
        try{

            FileInputStream fis = new FileInputStream("res/maps/world.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            // InputStream is = FileReader("res/maps/map1.txt");
            // BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while( col < gp.maxWorldCol){
                    String number[] = line.split(" ");

                    int num = Integer.parseInt(number[col]);

                    mapTileNum[col][row] = num;
                    col ++;

                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){


        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                }
            worldCol ++;

            
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;

                worldRow ++;
             
            }
        }


        // MAP etc
        // g2.drawImage(tile[0].image, 50, 50, gp.tileSize, gp.tileSize, null);

    }
}