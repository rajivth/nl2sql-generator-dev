package com.nl2sql.pipeline;

import com.nl2sql.schema.loader.SchemaSourceType;
import com.nl2sql.schema.metadata.llm.response.BusinessMetaResponse;
import com.nl2sql.schema.metadata.prompt.BusinessMetadataPayloadPrompt;
import com.nl2sql.schema.model.DatabaseSchema;
import com.nl2sql.vector.model.BusinessMetaDataVectorDoc;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PipelineContext {
    SchemaSourceType  schemaSourceType;
    List<String> ddlFiles;
    List<DatabaseSchema> schemaFiles;
    List<BusinessMetadataPayloadPrompt> businessLoadPrompts;
    List<BusinessMetaResponse> businessMetaResponses;
    List<BusinessMetaDataVectorDoc> vectorDocs;
}
