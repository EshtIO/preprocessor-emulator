package com.eshtio.preprocessor.core;

import com.eshtio.preprocessor.core.exception.IllegalDefinePropertyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Preprocessor define properties, support only boolean values.
 * All non boolean values will be transformed to 'false'
 *
 * Created by EshtIO on 2019-06-15.
 */
public class DefineProperties {

    private final Map<String, Boolean> map;

    private DefineProperties(Map<String, Boolean> map) {
        this.map = map;
    }

    /**
     * Read properties from file and create object
     *
     * @param filePath properties file
     * @return {@link DefineProperties}
     */
    public static DefineProperties read(String filePath) {
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Can't read define symbols file", e);
        }

        Map<String, Boolean> defineMap = new HashMap<>();
        Enumeration<?> names = properties.propertyNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            defineMap.put(key, Boolean.valueOf(properties.getProperty(key)));
        }
        return new DefineProperties(defineMap);
    }

    /**
     * Get property boolean value
     *
     * @param key property name
     * @return boolean
     */
    public boolean getValue(String key) {
        Boolean result = map.get(key);
        if (result == null) {
            throw new IllegalDefinePropertyException(key);
        }
        return result;
    }

}
