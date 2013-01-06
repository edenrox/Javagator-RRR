/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.sound;

import com.hopkins.rocknrollracing.Application;
import com.hopkins.rocknrollracing.utils.Inflector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.apache.log4j.Logger;

/**
 *
 * @author ian
 */
public class Larry {
    
    public static final String CHARCTER_PATH = "/sounds/characters/%s.wav";
    public static final String SOUND_PATH = "/sounds/%s.wav";
    public static final Logger logger = Logger.getLogger(Larry.class);
    
    protected Application app;
    protected boolean isPlaying;
    
    public Larry(Application theApp) {
        app = theApp;
    }
    
    public void playPodium(String character, int position, Object toNotify) {
        try {
            Clip nameClip = AudioSystem.getClip();
            Clip positionClip = AudioSystem.getClip();
            nameClip.open(getCharacterSound(character));
            positionClip.open(getPositionSound(position));
            
            nameClip.start();
            Thread.sleep(200);
            while (nameClip.isRunning()) {
                Thread.sleep(200);
            }
            positionClip.start();
            Thread.sleep(200);
            while (positionClip.isRunning()) {
                Thread.sleep(200);
            }
            nameClip.close();
            positionClip.close();
            
        } catch (Exception ex) {
            logger.error(String.format("error playing podium (%s %d)", character, position), ex);
        }
    }
    
    protected AudioInputStream getPositionSound(int position) throws Exception {
        String[] clipNames = new String[] {
            "", 
            "scores a first place knock out", 
            "finishes second", 
            "takes a weak third", 
            "is in another timezone"
        };
        
        return AudioSystem.getAudioInputStream(
                Application.class.getResourceAsStream(
                    String.format(SOUND_PATH, Inflector.underscore(clipNames[position]))));
    }
    
    protected AudioInputStream getCharacterSound(String characterName) throws Exception {
        return AudioSystem.getAudioInputStream(
                Application.class.getResourceAsStream(
                    String.format(CHARCTER_PATH, Inflector.underscore(characterName))));
    }
    
    
    
}
