package main;

public class setupGame {

    private String name = "Bambang";
    private String[] gender = {"male", "female"};
    private String farmName = "Bambang Farm";
    private String input = "hoho";

    public setupGame() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getInput() { return input; }
    
    private boolean nameInputActive = true;

    public boolean isNameInputActive() {
        return nameInputActive;
    }

    public void setNameInputActive(boolean active) {
        this.nameInputActive = active;
    }

    public void setInput(String input) {
        this.input = input;
    }

}
