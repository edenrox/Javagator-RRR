/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.model;

import com.hopkins.rocknrollracing.utils.Inflector;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ian
 */
public class NamedModelLookup<T extends NamedModel> {
    
    public static final String DATA_PATH = "data/%s.json";
    
    protected HashMap<String, T> data;
    
    public NamedModelLookup() {
        data = new HashMap<String, T>();
    }
    
    public T get(String name) {
        return data.get(name);
    }
    
    public int size() {
        return data.size();
    }
    public Set<String> keys() {
        return data.keySet();
    }
    
    public void load(Class modelClass) throws Exception {
        String className = modelClass.getSimpleName();
        
        // calculate the path for these models
        String path = String.format(DATA_PATH, Inflector.underscore(className));
        
        // read the data file
        JSONParser parser = new JSONParser();
        JSONArray objects = (JSONArray) parser.parse(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path)));
        for(Object object : objects) {
            T item = (T) modelClass.getConstructors()[0].newInstance();
            item.fromJSON((JSONObject) object);
            data.put(item.getName(), item);
        }
    }
}
