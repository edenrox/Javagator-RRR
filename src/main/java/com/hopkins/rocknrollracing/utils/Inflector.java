/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

import java.util.Arrays;

/**
 *
 * @author ian
 */
public class Inflector {
    
    public static final ArrayUtils.MapFunction<String> uppercaseFirstFunc = new ArrayUtils.MapFunction<String>() {
        public String execute(String item) {
            return uppercaseFirst(item);
        }
    };
    
    public static String underscore(String phrase) {
        return phrase.toLowerCase().replace(" ", "_").replace(".", "_");
    }
    public static String propercase(String phrase) {
        String[] words = phrase.replace("_", " ").split(" ");
        ArrayUtils.map(words, uppercaseFirstFunc);
        return StringUtils.join(words, "");
    }
    
    public static String uppercaseFirst(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
