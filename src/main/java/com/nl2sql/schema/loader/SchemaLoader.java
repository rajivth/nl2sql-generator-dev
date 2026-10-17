package com.nl2sql.schema.loader;

import java.util.List;

public interface SchemaLoader{
    List<String> load();
    SchemaSourceType getResourceType();
}
