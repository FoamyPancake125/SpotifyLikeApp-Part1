package com.spotifyapp;

import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {
    private Clip clip;
    private long pausePosition = 0;

    public void play(String filePath) {
        try {
            stop();
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Now playing: " + filePath);
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            System.out.println("Paused.");
        } else if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            System.out.println("Resumed.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            pausePosition = 0;
            System.out.println("Stopped.");
        }
    }

    public void rewind() {
        if (clip != null) {
            long pos = clip.getMicrosecondPosition() - 5_000_000;
            clip.setMicrosecondPosition(Math.max(pos, 0));
            System.out.println("Rewound 5 seconds.");
        }
    }

    public void forward() {
        if (clip != null) {
            long pos = clip.getMicrosecondPosition() + 5_000_000;
            clip.setMicrosecondPosition(Math.min(pos, clip.getMicrosecondLength()));
            System.out.println("Forwarded 5 seconds.");
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
