/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ian
 */
public class InjectUtils {
    public static final Logger log = Logger.getLogger(InjectUtils.class);
    
    public static void injectAll(Object obj) {
        List<Field> fields = getAllFields(obj);
        for(Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                boolean isLoadable = InjectLoadable.class.isAssignableFrom(fieldType);
                try {
                    Object instance = fieldType.getConstructors()[0].newInstance();
                    if (isLoadable) {
                        ((InjectLoadable) instance).load();
                    }
                    field.set(obj, instance);
                    /*log.debug(String.format(
                            "Injecting {class: %s, field: %s, type: %s, isLoadable: %s}",
                            obj.getClass().getName(), field.getName(), fieldType.toString(), isLoadable));*/
                } catch (Exception ex) {
                    log.error(String.format(
                            "Error injecting {field: %s, type: %s, isLoadable: %s}",
                            field.getName(), fieldType.toString(), isLoadable), ex);
                }
            }
        }
    }
    
    protected static List<Field> getAllFields(Object obj) {
        ArrayList<Field> rv = new ArrayList<Field>();
        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            rv.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return rv;
    }
    
}
