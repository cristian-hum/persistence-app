package com.fm.jdbc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesLoader {

    public static ApplicationProperties loadProperties() {
        // COMPLETED : use jackson to load properties from yml
        // YML factory

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        ApplicationProperties ap = new ApplicationProperties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("application.yml").getFile());

        Map<String, Map<String, String>> jsonMap = null;
        try {
            jsonMap = mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ap.setUrl(jsonMap.get("jdbc").get("url"));
        ap.setUsername(jsonMap.get("jdbc").get("username"));
        ap.setPassword(jsonMap.get("jdbc").get("password"));

        return ap;
    }

}
