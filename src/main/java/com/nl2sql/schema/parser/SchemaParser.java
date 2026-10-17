package com.nl2sql.schema.parser;


import com.nl2sql.schema.model.DatabaseSchema;

public interface SchemaParser {
    DatabaseSchema parse(String ddl);
}
