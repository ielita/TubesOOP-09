package tile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class mapCreator {
    
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;
    char[][] map = initializeMap(WIDTH, HEIGHT);

    public static void main(String[] args) {
        char[][] map = initializeMap(WIDTH, HEIGHT);
        addBorder(map);
        placeHouse(map);
        placePond(map);
        
        printMap(map);
        exportToTxt(map, "res/maps/uhuy.txt");
    }

    private static char[][] initializeMap(int width, int height) {
        char[][] map = new char[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                map[y][x] = '0'; // default tile
        return map;
    }

    private static void placeHouse(char[][] map) {
        Random rand = new Random();
        int houseW = 6, houseH = 6;
        int x, y;

        // Cari posisi house yang tidak keluar batas
        while (true) {
            x = rand.nextInt(WIDTH - houseW + 1);
            y = rand.nextInt(HEIGHT - houseH + 1);

            if (canPlace(map, x, y, houseW, houseH)) {
                fill(map, x, y, houseW, houseH, 'h');
                placeShippingBin(map, x, y, houseW, houseH);
                break;
            }
        }
    }

    private static void placeShippingBin(char[][] map, int houseX, int houseY, int houseW, int houseH) {
        int binW = 3, binH = 2;
        // Coba di sebelah kanan house
        int x = houseX + houseW + 1;
        int y = houseY;

        if (x + binW <= WIDTH && canPlace(map, x, y, binW, binH)) {
            fill(map, x, y, binW, binH, 's');
        } else {
            // fallback: coba kiri, atas, bawah
            int[][] offsets = {
                {houseX - binW - 1, y},        // left
                {houseX, houseY - binH - 1},   // top
                {houseX, houseY + houseH + 1}  // bottom
            };
            for (int[] offset : offsets) {
                int ox = offset[0], oy = offset[1];
                if (ox >= 0 && oy >= 0 && ox + binW <= WIDTH && oy + binH <= HEIGHT && canPlace(map, ox, oy, binW, binH)) {
                    fill(map, ox, oy, binW, binH, 's');
                    break;
                }
            }
        }
    }

    private static void placePond(char[][] map) {
        Random rand = new Random();
        int pondW = 4, pondH = 3;
        int x, y;

        // Cari posisi pond yang tidak bentrok
        while (true) {
            x = rand.nextInt(WIDTH - pondW + 1);
            y = rand.nextInt(HEIGHT - pondH + 1);

            if (canPlace(map, x, y, pondW, pondH)) {
                fill(map, x, y, pondW, pondH, 'o');
                break;
            }
        }
    }

    private static boolean canPlace(char[][] map, int startX, int startY, int w, int h) {
        for (int y = startY; y < startY + h; y++)
            for (int x = startX; x < startX + w; x++)
                if (map[y][x] != '0')
                    return false;
        return true;
    }

    private static void fill(char[][] map, int startX, int startY, int w, int h, char symbol) {
        for (int y = startY; y < startY + h; y++)
            for (int x = startX; x < startX + w; x++)
                map[y][x] = symbol;
    }

    private static void printMap(char[][] map) {
        for (char[] row : map) {
            for (char tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }

    private static void exportToTxt(char[][] map, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(WIDTH + " " + HEIGHT);
            writer.newLine();
            for (char[] row : map) {
                for (char tile : row) {
                    writer.write(tile + " ");
                }
                writer.newLine();
            }
            System.out.println("Map exported to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write file: " + e.getMessage());
        }
    }



    private static void addBorder(char[][] map) {
        for (int x = 0; x < WIDTH; x++) {
            map[0][x] = '4';               // Top row
            map[HEIGHT - 1][x] = '4';      // Bottom row
        }
        for (int y = 0; y < HEIGHT; y++) {
            map[y][0] = '4';               // Left column
            map[y][WIDTH - 1] = '4';       // Right column
        }
    }


}