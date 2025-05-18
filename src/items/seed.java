package items;

public class seed extends Item implements buysellable {
    season season; 
    int daysToHarvest;
    int price;
    public season genseason(){
        return season;
    } 
    public int getdaystoharvest(){
        return daysToHarvest;
    }
    public int getPrice(){
        return price;
    }
    public void setprice(int price){
        this.price = price;
    }
    public void setSeason(season season){
        this season = season;
    }
    public void setdaystoharvest(int daysToHarvest){
        this.daysToHarvest = daysToHarvest;
    }
}