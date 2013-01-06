/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author ihopkins
 */
public class KeyMap {
        
    public HashMap<Integer, ControllerButton> keyMap;

    public KeyMap() {
        keyMap = new HashMap<Integer, ControllerButton>();
    }

    public void load(InputStream is) throws IOException {
        // clear the current keymap
        keyMap.clear();

        // Load the key mappings from a properties file
        Properties props = new Properties();
        props.load(is);
        is.close();

        // Parse the properties file
        for(Object oKey : props.keySet()) {
            String sKey = (String) oKey;
            ControllerButton button = ControllerButton.valueOf(sKey);
            int value = Integer.parseInt(props.getProperty(sKey));

            keyMap.put(value, button);
        }
    }

    public ControllerButton getIndex(int key) {
        if (keyMap.containsKey(key)) {
            return keyMap.get(key);
        } else {
            return ControllerButton.Unknown;
        }
    }
}