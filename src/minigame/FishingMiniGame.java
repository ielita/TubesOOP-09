package minigame;

import java.util.Random;

public class FishingMiniGame {
    private items.fish pendingFish;
    private int answer;
    private int input = 0;
    private int tries = 0;
    private int min = 1;
    private int max = 10;
    private boolean active = false;

    public void start(items.fish fishToGet, int min, int max, int tries) {
        this.pendingFish = fishToGet;
        this.min = min;
        this.max = max;
        this.tries = tries;
        this.input = 0;
        this.answer = new Random().nextInt(max - min + 1) + min;
        this.active = true;
    }

    public int getInput() { return input; }
    public void setInput(int input) { this.input = input; }
    public int getTries() { return tries; }
    public void decTries() { this.tries--; }
    public boolean isActive() { return active; }
    public items.fish getPendingFish() { return pendingFish; }
    public int getAnswer() { return answer; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public void finish() { active = false; pendingFish = null; input = 0; tries = 0; }
    public void resetInput() { this.input = 0; }
}