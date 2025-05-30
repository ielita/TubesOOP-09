package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.FloatControl;

public class Sound{
    
    Clip clip;
    File soundFile[] = new File[30];

    public Sound(){
        
        soundFile[0] = new File("res/sound/king.wav");

    }

    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e){

        }

        // // Set volume using FloatControl
        // FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // float volume = 0.5f; // range: 0.0 (mute) to 1.0 (max)
        // float min = gainControl.getMinimum();
        // float max = gainControl.getMaximum();
        // float dB = min + (max - min) * volume;
        // gainControl.setValue(dB);s

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