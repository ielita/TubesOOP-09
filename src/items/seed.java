package items;
import main.GamePanel;

public class seed extends Item implements buysellable {
    private int hargabeli;
    private int hargajual;
    private String cropType;
    private int growthTime;
    private String season;

    public seed(String name, GamePanel gp, String cropType, int growthTime, int hargabeli, int hargajual, String season) {
        super(name, gp);
        this.cropType = cropType;
        this.growthTime = growthTime;
        this.hargabeli = hargabeli;
        this.hargajual = hargajual;
        this.season = season;
    }

    // Getters and setters
    public int getHargaBeli() {
        return hargabeli;
    }

    public void setHargabeli(int hargabeli) {
        this.hargabeli = hargabeli;
    }

    @Override
    public int getHargaJual() {
        return hargajual;
    }

    public void setHargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public String getCropType() {
        return cropType;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }

    public int getCropYield() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "wheat": return 3;
            case "blueberry": return 3;
            case "cranberry": return 10;
            case "grape": return 20;
            default: return 1;
        }
    }
    public int getCropSellPrice() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "parsnip": return 35;
            case "cauliflower": return 150;
            case "potato": return 80;
            case "wheat": return 30;
            case "blueberry": return 40;
            case "tomato": return 60;
            case "hot pepper": return 40;
            case "melon": return 250;
            case "cranberry": return 25;
            case "pumpkin": return 250;
            case "grape": return 10;
            default: return 0;
        }
    }

    // Get the buy price for the resulting crop
    public int getCropBuyPrice() {
        String cropType = this.cropType.toLowerCase();
        
        switch (cropType) {
            case "parsnip": return 50;
            case "cauliflower": return 200;
            case "potato": return 0; 
            case "wheat": return 50;
            case "blueberry": return 150;
            case "tomato": return 90;
            case "hot pepper": return 0; 
            case "melon": return 0;
            case "cranberry": return 0; 
            case "pumpkin": return 300;
            case "grape": return 100;
            default: return 0;
        }
    }

    // Plant this seed and return the resulting crop when ready
    public crop plant() {

        crop harvestedCrop = new crop(
            this.cropType, 
            this.gp,
            getCropBuyPrice(),
            getCropSellPrice(), 
            getCropYield()
        );
        return harvestedCrop;
    }

    // Calculate total revenue from one harvest
    public int getTotalRevenue() {
        return getCropSellPrice() * getCropYield();
    }



    // Check if this is a multi-harvest crop
    public boolean isMultiHarvest() {
        String cropLower = cropType.toLowerCase();
        return cropLower.equals("wheat") || cropLower.equals("blueberry") || 
               cropLower.equals("cranberry") || cropLower.equals("grape");
    }


    // Implement abstract method from Item class
    @Override
    public void getinfo() {

    }
}