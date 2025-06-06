package minigame;

import java.util.Random;
import items.fish;

public class FishingMiniGame {
    private fish pendingFish;
    private int answer;
    private int input = -1;
    private int tries = 0;
    private int min = 1;
    private int max = 10;
    private boolean active = false;
    private fish resultItem;
    private String resultMessage;

    public void start(fish fishToGet, int min, int max, int tries) {
        this.pendingFish = fishToGet;
        this.min = min;
        this.max = max;
        this.tries = tries;
        this.input = -1;
        // this.answer = new Random().nextInt((max - min) + 1) + min;
        this.answer =1;
        this.active = true;
        this.resultItem = null;
        this.resultMessage = "";
    }

    public int getInput() {
        return input == -1 ? 0 : input;
    }

    public void addDigit(int digit) {
        if (input == -1) input = 0;
        int next = input * 10 + digit;
        if (next >= min && next <= max) {
            input = next;
        }
    }

    public void backspace() {
        if (input >= 10) {
            input /= 10;
        } else {
            input = -1;
        }
    }

    public void resetInput() {
        input = -1;
    }

    public int getTries() {
        return tries;
    }

    public void decTries() {
        this.tries--;
    }

    public boolean isActive() {
        return active;
    }

    public items.fish getPendingFish() {
        return pendingFish;
    }

    public int getAnswer() {
        return answer;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void processGuess(int guess, entity.Player player) {
        if (guess == answer) {
            player.addItemToInventory(pendingFish, 1);
            resultItem = pendingFish;
            resultMessage = "Selamat! Anda berhasil mendapatkan " + pendingFish.getName();
            finish();
        } else {
            decTries();
            if (tries <= 0) {
                resultItem = null;
                resultMessage = "Anda gagal mendapatkan ikan!";
                finish();
            } else {
                resetInput();
            }
        }
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public fish getResultItem() {
        return resultItem;
    }

    public void finish() {
        active = false;
        pendingFish = null;
        input = -1;
        tries = 0;
    }
}