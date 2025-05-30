package main;
import java.util.Random;

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
    
    public class Weather{
        private final int SUNNY = 0;
        private final int RAINY = 1;
        private int[] weatherPattern;
        public Weather() {
            weatherPattern = new int[DAYS_PER_SEASON];
            generateWeatherPattern();
        }
        private void generateWeatherPattern() {
            Random random = new Random();
            int rainyDays = 2 + random.nextInt(3); 
            int sunnyDays = DAYS_PER_SEASON - rainyDays;
            for (int i = 0; i < sunnyDays; i++) {
                weatherPattern[i] = SUNNY;
            }

            for (int i = 0; i < rainyDays; i++) {
                int index;
                do {
                    index = random.nextInt(DAYS_PER_SEASON);
                } while (weatherPattern[index] != SUNNY);
                weatherPattern[index] = RAINY;
            }
        }

        public int getWeatherForDay(int day) {
            return weatherPattern[day - 1];
        }


    }

    public Weather weather;

    public TimeManager(GamePanel gp) {
        this.weather = new Weather();
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
                        
                        System.out.println("New day started: Day " + day);

                        // Check for season change first
                        if (day > DAYS_PER_SEASON) {
                            day = 1;
                            currentSeasonIndex = (currentSeasonIndex + 1) % 4;
                            season = SEASONS[currentSeasonIndex];
                            weather = new Weather(); // Generate new weather for new season
                            System.out.println("New season: " + season);
                        }

                        // Apply weather effects and update plants
                        if (gp != null && gp.tileM != null && gp.tileM.mapManager != null) {
                            gp.tileM.mapManager.rainyDay(); // This will now work
                            gp.tileM.mapManager.updatePlantGrowth();
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
        
        if (isRainyDay()) {
            brightness *= 0.7f; 
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
        }
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
        
        System.out.println("Skipping to day: " + day);
        
        if (day > DAYS_PER_SEASON) {
            day = 1;
            currentSeasonIndex = (currentSeasonIndex + 1) % 4;
            season = SEASONS[currentSeasonIndex];
            weather = new Weather();
            System.out.println("New season: " + season);
        }
        
        updateBrightness();
        
        if (gp != null && gp.tileM != null && gp.tileM.mapManager != null) {
            gp.tileM.mapManager.rainyDay();
            gp.tileM.mapManager.updatePlantGrowth();
        }
    }
    public boolean isRainyDay() {
        return weather.getWeatherForDay(day) == 1;
    }
}
