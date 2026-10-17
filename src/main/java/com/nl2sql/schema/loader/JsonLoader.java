package com.nl2sql.schema.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JsonLoader {

    @Value("classpath*:json/*.json")
    private Resource[] classpathResource;

    public JsonLoader(Resource[] classpathResource) {
        this.classpathResource = classpathResource;
    }


    public List<JsonFile> load() {
        log.info("Loading {} JSON files from classpath", classpathResource.length);
        return Arrays.stream(classpathResource).map(resource -> {
            var documents = new TextReader(resource).get();
            String content = documents.stream().map(Document::getText).collect(Collectors.joining());
            return new JsonFile(resource.getFilename(), content);
        }).toList();
    }

}
