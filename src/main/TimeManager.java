package main;

public class TimeManager {
    GamePanel gp;
    private int hour;
    private int minute;
    private int day;
    private String season;
    private long lastUpdateTime;
    private final long UPDATE_INTERVAL = 1000; // 1 second real time
    private final int MINUTES_PER_UPDATE = 5;  // 5 minutes game time
    private final int DAYS_PER_SEASON = 10;    // 10 days per season
    private final String[] SEASONS = {"Spring", "Summer", "Fall", "Winter"};
    private int currentSeasonIndex;
    
    // Add this after other fields
    private final float DAY_BRIGHTNESS = 1.0f;
    private final float NIGHT_BRIGHTNESS = 0.3f;
    private final int DAWN_HOUR = 6;  // 6:00 AM
    private final int DUSK_HOUR = 18; // 6:00 PM
    private final int TRANSITION_DURATION = 2; // Hours for sunrise/sunset

    private boolean newDay = false; // Tambahkan ini

    public TimeManager(GamePanel gp) {
        this.gp = gp;
        hour = 8;      // Start at 6:00
        minute = 0;
        day = 1;       // Start at day 1
        currentSeasonIndex = 0;  // Start in Spring
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
                        newDay = true; // Set flag newDay
                        System.out.println("=== NEW DAY TRIGGERED: " + getDateString() + " ===");

                        // Check for season change
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
            newDay = false; // Reset flag after checking
            System.out.println("isNewDay() returning TRUE and resetting flag");
            return true;
        }
        return false;
    }

    // Also add a method to check without consuming the flag
    public boolean checkNewDayFlag() {
        return newDay;
    }


    private void updateBrightness() {
        float brightness;
        
        if (hour >= DAWN_HOUR && hour < DUSK_HOUR) {
            // Daytime
            if (hour < DAWN_HOUR + TRANSITION_DURATION) {
                // Sunrise transition
                float progress = (hour + (minute / 60.0f) - DAWN_HOUR) / TRANSITION_DURATION;
                brightness = NIGHT_BRIGHTNESS + (DAY_BRIGHTNESS - NIGHT_BRIGHTNESS) * progress;
            } else if (hour >= DUSK_HOUR - TRANSITION_DURATION) {
                // Sunset transition
                float progress = (DUSK_HOUR - (hour + (minute / 60.0f))) / TRANSITION_DURATION;
                brightness = NIGHT_BRIGHTNESS + (DAY_BRIGHTNESS - NIGHT_BRIGHTNESS) * progress;
            } else {
                // Full daylight
                brightness = DAY_BRIGHTNESS;
            }
        } else {
            // Nighttime
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid time format. Use HH:MM");
        }
    }
    
    public String getTimeString() {
        String hourStr = String.format("%02d", hour);
        String minStr = String.format("%02d", minute);
        return hourStr + ":" + minStr;
    }

    public void setNight(boolean isNight) {
        if (isNight) {
            setHour(18); // Set to 18:00 for night
            gp.tileM.mapManager.setBrightness(0.3f);
        } else {
            setHour(6);  // Set to 06:00 for day
            gp.tileM.mapManager.setBrightness(1.0f); // Also add brightness for day
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

    // Add this method for the cheat
    public void skipDay() {
        // Reset time to 6:00 AM (start of new day)
        hour = 6;
        minute = 0;
        
        // Increment day
        day++;
        newDay = true; // Trigger new day flag
        System.out.println("=== SKIP DAY TRIGGERED: " + getDateString() + " ===");

        // Check for season change
        if (day > DAYS_PER_SEASON) {
            day = 1;
            currentSeasonIndex = (currentSeasonIndex + 1) % 4;
            season = SEASONS[currentSeasonIndex];
        }
        
        // Update brightness to day time
        updateBrightness();
        
        System.out.println("Day skipped! New date: " + getDateString());
        
        // Update plant growth - hanya untuk farm
        if (gp != null && gp.tileM != null && gp.tileM.mapManager != null) {
            gp.tileM.mapManager.updatePlantGrowth();
        }
    }
}