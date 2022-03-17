package com.fm.jdbc.util;

import lombok.Data;

@Data
public class ApplicationProperties {
    private String url;
    private String username;
    private String password;
    private boolean createDatabaseIfNotExist;
}
