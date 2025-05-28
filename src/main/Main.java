package main;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.ImageIcon;

public class Main{
    

    public static JFrame window; 
    public static void main(String[]args){

        ImageIcon logo = null;
        try {

            logo = new ImageIcon(ImageIO.read(new File("res/icon/champion.png")));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            e.printStackTrace(); 
        }

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Spakbor si Petani");
        window.setIconImage(logo.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();


    }
    
}