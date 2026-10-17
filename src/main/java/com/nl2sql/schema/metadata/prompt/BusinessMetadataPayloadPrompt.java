package com.nl2sql.schema.metadata.prompt;

import lombok.Builder;
import org.springframework.ai.chat.prompt.Prompt;

@Builder
public record BusinessMetadataPayloadPrompt(Prompt tablePrompt, Prompt columnPrompt, Prompt relationshipPrompt) {
}
