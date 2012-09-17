/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

/**
 *
 * @author ian
 */
public class StringUtils {
    
    public static String join(String[] items, String glue) {
        StringBuilder sb = new StringBuilder();
        for(String item : items) {
            sb.append(item);
            sb.append(glue);
        }
        sb.setLength(sb.length() - glue.length());
        return sb.toString();
    }
}
