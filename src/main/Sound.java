package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound{
    
    Clip clip;
    File soundFile[] = new File[30];

    public Sound(){
        
        soundFile[0] = new File("res/sound/forestquiet.wav");

    }

    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void play(){

        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){

        clip.stop();
    }

}