package com.nl2sql.schema.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchemaLoaderFactory {
    private final Map<SchemaSourceType, SchemaLoader> schemaLoaders;

    public SchemaLoaderFactory(List<SchemaLoader> loaders) {
        this.schemaLoaders = loaders.stream().collect(Collectors.toMap(SchemaLoader::getResourceType, Function.identity()));
    }

    public List<String> loadSchemas(SchemaSourceType sourceType) {
        log.info("Loading schema from {}", sourceType);
        return Optional.ofNullable(schemaLoaders.get(sourceType)).
                orElseThrow(() -> new IllegalArgumentException("No SchemaLoader registered for: " + sourceType)).load();
    }
}
