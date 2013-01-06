/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.animation;

import java.awt.Graphics;

/**
 *
 * @author ihopkins
 */
public abstract class Animation {
    protected long globalFrameOffset;
    protected KeyFrame[] frames;
    protected int currentFrameIndex;
    
    public void setKeyFrames(KeyFrame[] frames) {
        this.frames = frames;
    }
    
    public void start(long globalFrame) {
        globalFrameOffset = globalFrame;
        currentFrameIndex = 0;
    }
    
    public void render(Graphics g, long globalFrame) {
        
        // figure out the relative frame we are on
        long frame = globalFrame - globalFrameOffset;
        
        if (!isLastKeyFrame()) {
            // Advance the frame if needed
            if (frame >= nextKeyFrame().frame) {
                currentFrameIndex++;
            }
        }
        
        double frameCoefficient = 0.0;
        double translationCoefficient = 0.0;
        
        if (!isLastKeyFrame()) {
            frameCoefficient = (double) (frame - currentKeyFrame().frame) / (nextKeyFrame().frame - currentKeyFrame().frame);
            
            
        }
        int x = calculatePosition(currentKeyFrame().x, nextKeyFrame().x, translationCoefficient);
        int y = calculatePosition(currentKeyFrame().y, nextKeyFrame().y, translationCoefficient);
        
        renderFrame(g, x, y, frame);
    }
    
    protected int calculatePosition(int start, int end, double translationCoefficient) {
        return (int) (start + (end - start) * translationCoefficient);
    }
    
    protected boolean isLastKeyFrame() {
        return (currentFrameIndex == frames.length - 1);
    }
    
    protected KeyFrame currentKeyFrame() {
        return frames[currentFrameIndex];
    }
    protected KeyFrame nextKeyFrame() {
        return frames[currentFrameIndex + 1];
    }
    
    protected abstract void renderFrame(Graphics g, int x, int y, long frame);
    
    protected double getTranslationCoefficient(double c, Easing easing) {
        switch (easing) {
            case Linear:
                return c;
            case In:
                return c * c;
            case Out:
                return 1 - (c - 1) * (c - 1);
            case InOut:
                if (c < 0.5) {
                    return 0.5 * (2 * c) * (2 * c);
                } else {
                    return -2 * c * c + 4 * c - 1;
                }
            case None:
            default:
                return 0.0;
        }
    }
    
}
