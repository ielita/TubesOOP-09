package main;

public class TimeManager {
    GamePanel gp;
    private int hour;
    private int minute;
    private int day;
    private String season;
    private long lastUpdateTime;
    private final long UPDATE_INTERVAL = 1000;
    private final int MINUTES_PER_UPDATE = 5;
    private final int DAYS_PER_SEASON = 10;
    private final String[] SEASONS = {"Spring", "Summer", "Fall", "Winter"};
    private int currentSeasonIndex;
    private final float DAY_BRIGHTNESS = 1.0f;
    private final float NIGHT_BRIGHTNESS = 0.3f;
    private final int DAWN_HOUR = 6;
    private final int DUSK_HOUR = 18;
    private final int TRANSITION_DURATION = 2;
    private boolean newDay = false;

    public TimeManager(GamePanel gp) {
        this.gp = gp;
        hour = 8;
        minute = 0;
        day = 1;
        currentSeasonIndex = 0;
        season = SEASONS[currentSeasonIndex];
        lastUpdateTime = System.currentTimeMillis();
    }

    public void update() {
        if (gp.gameState == gp.playState) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdateTime >= UPDATE_INTERVAL) {
                minute += MINUTES_PER_UPDATE;
                if (minute >= 60) {
                    hour += minute / 60;
                    minute %= 60;
                    updateBrightness();
                    if (hour >= 24) {
                        hour = 0;
                        day++;
                        newDay = true;
                        if (day > DAYS_PER_SEASON) {
                            day = 1;
                            currentSeasonIndex = (currentSeasonIndex + 1) % 4;
                            season = SEASONS[currentSeasonIndex];
                        }
                    }
                }
                lastUpdateTime = currentTime;
            }
        }
    }

    public boolean isNewDay() {
        if (newDay) {
            newDay = false;
            return true;
        }
        return false;
    }

    public boolean checkNewDayFlag() {
        return newDay;
    }

    private void updateBrightness() {
        float brightness;
        if (hour >= DAWN_HOUR && hour < DUSK_HOUR) {
            if (hour < DAWN_HOUR + TRANSITION_DURATION) {
                float progress = (hour + (minute / 60.0f) - DAWN_HOUR) / TRANSITION_DURATION;
                brightness = NIGHT_BRIGHTNESS + (DAY_BRIGHTNESS - NIGHT_BRIGHTNESS) * progress;
            } else if (hour >= DUSK_HOUR - TRANSITION_DURATION) {
                float progress = (DUSK_HOUR - (hour + (minute / 60.0f))) / TRANSITION_DURATION;
                brightness = NIGHT_BRIGHTNESS + (DAY_BRIGHTNESS - NIGHT_BRIGHTNESS) * progress;
            } else {
                brightness = DAY_BRIGHTNESS;
            }
        } else {
            brightness = NIGHT_BRIGHTNESS;
        }
        gp.tileM.mapManager.setBrightness(brightness);
    }

    public void setHour(int hour) {
        if (hour >= 0 && hour < 24) {
            this.hour = hour;
        }
    }

    public int getHour() {
        return hour;
    }

    public void setMinute(int minute) {
        if (minute >= 0 && minute < 60) {
            this.minute = minute;
        }
    }

    public int getMinute() {
        return minute;
    }

    public void setTime(int hour, int minute) {
        setHour(hour);
        setMinute(minute);
    }

    public void setTimeString(String timeString) {
        try {
            String[] parts = timeString.split(":");
            if (parts.length == 2) {
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                setTime(hour, minute);
            }
        } catch (NumberFormatException e) {}
    }

    public String getTimeString() {
        String hourStr = String.format("%02d", hour);
        String minStr = String.format("%02d", minute);
        return hourStr + ":" + minStr;
    }

    public void setNight(boolean isNight) {
        if (isNight) {
            setHour(18);
            gp.tileM.mapManager.setBrightness(0.3f);
        } else {
            setHour(6);
            gp.tileM.mapManager.setBrightness(1.0f);
        }
    }

    public boolean isNight() {
        return hour >= 18 || hour < 6;
    }

    public String getDateString() {
        return season + " Day " + day;
    }

    public int getDay() {
        return day;
    }

    public String getSeason() {
        return season;
    }

    public void skipDay() {
        hour = 6;
        minute = 0;
        day++;
        newDay = true;
        if (day > DAYS_PER_SEASON) {
            day = 1;
            currentSeasonIndex = (currentSeasonIndex + 1) % 4;
            season = SEASONS[currentSeasonIndex];
        }
        updateBrightness();
        if (gp != null && gp.tileM != null && gp.tileM.mapManager != null) {
            gp.tileM.mapManager.updatePlantGrowth();
        }
    }
}