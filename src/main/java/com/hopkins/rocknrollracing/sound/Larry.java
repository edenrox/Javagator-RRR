/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.sound;

import com.hopkins.rocknrollracing.utils.Inflector;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import org.apache.log4j.Logger;

/**
 *
 * @author ian
 */
public class Larry implements LineListener {
    public static final String SOUND_PATH = "/sounds/%s.wav";
    public static final Logger logger = Logger.getLogger(Larry.class);
    
    protected Clip theClip;
    
    public Larry() {
        theClip = null;
    }
    
    protected AudioInputStream loadClip(String clipName) throws Exception {
        return AudioSystem.getAudioInputStream(
                Larry.class.getResourceAsStream(
                    String.format(SOUND_PATH, clipName)));
    }
    
    protected List<AudioInputStream> loadClips(String[] clipNames) {
        ArrayList<AudioInputStream> rv = new ArrayList<AudioInputStream>();
        
        for(String clipName : clipNames) {
            try {
                rv.add(loadClip(Inflector.underscore(clipName)));
            } catch (Exception ex) {
                logger.error("Error loading clip: " + clipName, ex);
            }
        }
        
        return rv;
    }
    
    protected AudioInputStream concatenateClips(List<AudioInputStream> streams) {
        long length = 0;
        for(AudioInputStream item : streams) {
            length += item.getFrameLength();
        }
        return new AudioInputStream(
                new SequenceInputStream(Collections.enumeration(streams)),
                streams.get(0).getFormat(),
                length);
    }
    
    public synchronized void startPlaying(String... clipNames) {
        AudioInputStream ais = concatenateClips(loadClips(clipNames));
        
        try {
            theClip = AudioSystem.getClip();
            theClip.open(ais);
            theClip.addLineListener(this);
            theClip.start();
        } catch (Exception ex) {
            logger.error("Error playing clip", ex);
        }
    }
    
    public synchronized void waitUntilFinished() throws InterruptedException {
        this.wait();
    }
    
    public synchronized boolean isPlaying() {
        return (theClip != null);
    }
    
    public synchronized void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            theClip.close();
            theClip = null;
            this.notify();
        }
    }
    
    
    
}
