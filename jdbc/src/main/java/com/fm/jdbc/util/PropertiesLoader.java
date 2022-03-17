package com.fm.jdbc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class PropertiesLoader {

    public ApplicationProperties loadProperties() throws IOException {
        // TODO: use jackson to load properties from yml
        // YML factory

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        return mapper.readValue(new File("src/main/resources/application.yml"), ApplicationProperties.class);
    }

}