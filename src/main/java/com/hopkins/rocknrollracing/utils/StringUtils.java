/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

import java.text.DecimalFormat;

/**
 *
 * @author ian
 */
public class StringUtils {
    
    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###");
    
    public static String formatNumber(int number) {
        return NUMBER_FORMAT.format(number);
    }
    
    public static String join(String[] items, String glue) {
        StringBuilder sb = new StringBuilder();
        for(String item : items) {
            sb.append(item);
            sb.append(glue);
        }
        sb.setLength(sb.length() - glue.length());
        return sb.toString();
    }
    
    public static String nameToPath(String name) {
        return name.toLowerCase().replace(' ', '_').replace('.', '_');
    }
}
