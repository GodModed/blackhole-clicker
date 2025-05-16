package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    File audioFile;

    public Sound(File file) throws Exception {
        this.audioFile = file;
    }

    public void play() {
        // play sound in a new thread so multiple sounds can be played at the same time
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // read sound from file
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start(); // play sound
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException("Could not get audio system / load file stream.", e);
            }
            
        }).start();
    }
}
