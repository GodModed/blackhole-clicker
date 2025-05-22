package game;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    File audioFile;
    byte[] audioData;

    public Sound(File file) throws Exception {
        this.audioFile = file;
    }

    public void play() {
        // play sound in a new thread so multiple sounds can be played at the same time
        new Thread(() -> {
            try {
                if (audioData == null) {
                    try (FileInputStream stream = new FileInputStream(audioFile)) {
                        audioData = stream.readAllBytes();
                    }
                }
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData)); // read sound from file
                Clip clip = AudioSystem.getClip();
                clip.addLineListener((LineEvent e) -> {
                    if (e.getType() == LineEvent.Type.STOP) clip.close(); // close sound when it is finished. prevent memory leak
                });
                clip.open(audioStream);
                clip.start(); // play sound
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException("Could not get audio system / load file stream.", e);
            }
            
        }).start();
    }
}
