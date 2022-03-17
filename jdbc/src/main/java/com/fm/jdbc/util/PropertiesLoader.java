package com.fm.jdbc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class PropertiesLoader {

    public static ApplicationProperties loadProperties() {
        // COMPLETED : use jackson to load properties from yml
        // YML factory

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        ApplicationProperties ap = null;

        {
            try {
                ap = mapper.readValue(new File("src/main/resources/application.yml"), ApplicationProperties.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ap;
    }

}
