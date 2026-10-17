package com.nl2sql.api.controller;

import com.nl2sql.pipeline.MetadataPipeline;
import com.nl2sql.schema.loader.SchemaSourceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schema")
@RequiredArgsConstructor
@Slf4j
public class SchemaController {

   /* final SchemaLoaderFactory schemaLoaderFactory;
    final SchemaParser schemaParser;
    final LlmBusinessMetaAnalyzer businessMetaAnalyzer;
    final PromptBuilder<DatabaseSchema, BusinessMetadataPayloadPrompt> promptBuilder;
    final JacksonConverter jacksonConverter;
    final MetadataHandlerService metadataHandlerService;
    final MetadataDocumentStep metadataDocumentStep;
    final VectorStoreStep vectorStoreStep;

    @Value("classpath:json/business-meta-resp.txt")
    private Resource businessMetaRespResource;*/
    final MetadataPipeline metadataPipeline;

    @GetMapping("/load")
    public ResponseEntity<String> loadSchema() {
        log.info("Loading schema files from source: {}", SchemaSourceType.CLASSPATH);
        metadataPipeline.execute(SchemaSourceType.CLASSPATH);
        return ResponseEntity.ok("Metadata pipeline completed");
    }

   /* @GetMapping("/generate")
    public ResponseEntity<BusinessMetaResponse> generateMetaData() {
        log.info("Generate Business Meta data  ");
        var responseDatabaseSchemaList = loadSchema();
        var businessMetadataPayloadPrompts = responseDatabaseSchemaList.getBody().stream().map(promptBuilder::build).toList();
        var businessMetaResponses = businessMetadataPayloadPrompts.stream().map(businessMetaAnalyzer::businessMetaGeneration).toList();
        return ResponseEntity.ok(businessMetaResponses.get(0));
    }

    @GetMapping("/save")
    public ResponseEntity<Void> saveMetaData() {

        log.info("Starting business metadata persistence");

        try {
            String json = businessMetaRespResource.getContentAsString(StandardCharsets.UTF_8);
            BusinessMetaResponse response = jacksonConverter.convertJsonToObject(json, BusinessMetaResponse.class);
            //metadataHandlerService.saveMeta(response);
            PipelineContext context = new PipelineContext();
            context.setBusinessMetaResponses(Arrays.asList(response));
            metadataDocumentStep.process(context);
            vectorStoreStep.process(context);

            log.info("Business metadata persisted successfully");
            return ResponseEntity.ok().build();
        } catch (IOException e) {

            log.error(
                    "Failed to read business metadata resource", e
            );


        }
        return null;
    }*/
}
