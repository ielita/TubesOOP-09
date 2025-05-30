package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public boolean timeInRange(String current, String range) {
        
        try {
            String[] parts = range.split("–");
            if (parts.length != 2) return false;

            int start = Integer.parseInt(parts[0].replace(".", ""));
            int end = Integer.parseInt(parts[1].replace(".", ""));
            int now = Integer.parseInt(current.replace(":", ""));

            
            if (start < end) {
                return now >= start && now < end;
            } else {
                
                return now >= start || now < end;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}