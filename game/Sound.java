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
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException("Could not get audio system / load file stream.", e);
            }
            
        }).start();
    }
}
