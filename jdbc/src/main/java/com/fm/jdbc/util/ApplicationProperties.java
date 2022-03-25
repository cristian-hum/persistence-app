package com.fm.jdbc.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplicationProperties {

    @JsonProperty("jdbc")
    private JdbcProperties jdbcProperties;

    private String version;
}
