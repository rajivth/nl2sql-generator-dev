package com.nl2sql.schema.loader;

import lombok.Getter;

@Getter
public enum SchemaSourceType {

    CLASSPATH("classpath"),
    GIT("git"),
    LOCAL("local"),
    S3("s3");

    private final String type;

    SchemaSourceType(String type) {
        this.type = type;
    }

}
