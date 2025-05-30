package main;

public class setupGame {

    private String name = "Bambang";
    private String[] gender = {"male", "female"};
    private String farmName = " Farm";
    private String input = "Bambang";

    private String input2 = "Farm";

    public String getInput() { return input; }
    public String getInput2() { return input2; }
    public void setInput2(String input2) { 
        this.input2 = input2; 
    }
    public void setInput(String input) {
        this.input = input;
    }
    
    public setupGame() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    
    private boolean nameInputActive = true;

    public boolean isNameInputActive() {
        return nameInputActive;
    }

    public void setNameInputActive(boolean active) {
        this.nameInputActive = active;
    }

    
    public String getName() {
        return name;
    }

    public String getFarmName() {
        return farmName;
    }
}