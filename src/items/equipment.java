package items;

public class equipment extends Item{
    public equipment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void equip(){

    }
    public void use(){
        
    }

    public void getinfo(){
        System.out.println("This is an equipment item.");
    }
}