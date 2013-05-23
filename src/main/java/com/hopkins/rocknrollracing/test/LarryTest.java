/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.sound.Larry;

/**
 *
 * @author ian
 */
public class LarryTest {
    public static void main(String[] args) throws Exception {
        Larry l = new Larry();
        
        /*l.playClips("characters/cyberhawk", "scores a first place knock out",
            "characters/jake badlands", "finishes second",
            "characters/olaf", "takes a weak third",
            "characters/rip", "is in another timezone");
                "the stage is set the green flag drops", 
                "let the carnage begin", "ouch", "holy toledo", 
                "shred", "should avoid mines",
                "ivanzypher", "jams into first",
                "roadkill kelly", "looks lost out there"
                );*/
        /*l.startPlaying("let the carnage begin");
        l.waitUntilFinished();
        l.startPlaying("j.b. slash", "unleashes hot fury");
        l.waitUntilFinished();
        l.startPlaying("olaf", "scores a first place knockout");
        l.waitUntilFinished();
        l.startPlaying("ivanzypher", "jams into first");
        l.waitUntilFinished();
        l.startPlaying("rip", "is headed the wrong way");
        l.waitUntilFinished();
        l.startPlaying("viper mackay", "hits the warp");
        l.waitUntilFinished();
        l.startPlaying("grinder x19", "is dominating the race");
        l.waitUntilFinished();*/
        
        l.startPlaying("tarquinn", "launches himself");
        l.waitUntilFinished();
        l.startPlaying("shred", "gets hammered");
        l.waitUntilFinished();
        
        System.err.println("Done");
    }
}
