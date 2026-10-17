package com.nl2sql.schema.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClassPathSchemaLoader implements SchemaLoader {

    @Value("classpath*:schemas/*.txt")
    private Resource[] classpathResource;

    public ClassPathSchemaLoader(Resource[] classpathResource) {
        this.classpathResource = classpathResource;
    }

    @Override
    public List<String> load() {
        log.info("Loading {} schema files from classpath", classpathResource.length);
        return Arrays.stream(classpathResource).map(TextReader::new).
                map(TextReader::get).flatMap(List::stream).map(Document::getText).toList();
    }

    @Override
    public SchemaSourceType getResourceType() {
        return SchemaSourceType.CLASSPATH;
    }
}
