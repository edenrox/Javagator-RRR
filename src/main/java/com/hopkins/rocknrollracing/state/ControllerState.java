/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author ihopkins
 */
public class ControllerState implements KeyListener {
    
    public static final Logger log = Logger.getLogger(ControllerState.class);
    public static final ControllerButton[] ANY_BUTTONS = new ControllerButton[] {
        ControllerButton.Pause, ControllerButton.Boost, ControllerButton.Drop,
        ControllerButton.Fire, ControllerButton.Gas};

    protected ButtonState buttonStates[];
    protected KeyMap map;
    
    public ControllerState() {
        int size = ControllerButton.values().length;
        buttonStates = new ButtonState[size];
        map = new KeyMap();
        for(int i = 0; i < buttonStates.length; i++) {
            buttonStates[i] = ButtonState.Up;
        }
    }
    
    public boolean isDown(ControllerButton button) {
        return buttonStates[getIndex(button)] != ButtonState.Up;
    }
    
    public boolean isPressed(ControllerButton button) {
        return buttonStates[getIndex(button)] == ButtonState.Pressed;
    }
    
    public boolean isAnyButtonPressed() {
        for(ControllerButton button : ANY_BUTTONS) {
            if (isPressed(button)) {
                return true;
            }
        }
        return false;
    }
    
    public void loadConfig(String filename) {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
            map.load(is);
        } catch (Exception ex) {
            log.error("Could not read key map", ex);
        }
    }
    
    public void afterUpdate() {
        for(int i = 0; i < buttonStates.length; i++) {
            if (buttonStates[i] == ButtonState.Pressed) {
                buttonStates[i] = ButtonState.Down;
            }
        }
    }
    
    protected int getIndex(ControllerButton button) {
        return button.ordinal();
    }
    
    
    public void keyPressed(KeyEvent ke) {
        ControllerButton button = map.getIndex(ke.getKeyCode());
        buttonStates[getIndex(button)] = ButtonState.Pressed;
    }

    public void keyReleased(KeyEvent ke) {
        ControllerButton button = map.getIndex(ke.getKeyCode());
        buttonStates[getIndex(button)] = ButtonState.Up;
    }

    public void keyTyped(KeyEvent ke) {
        // noop
    }
    
    
    public static enum ButtonState {
        Up,
        Down,
        Pressed
    }
}
