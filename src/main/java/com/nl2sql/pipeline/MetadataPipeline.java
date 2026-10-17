package com.nl2sql.pipeline;

import com.nl2sql.schema.loader.SchemaSourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MetadataPipeline {
    final List<Pipeline> steps;

    public PipelineContext execute(SchemaSourceType  schemaSourceType){
            var context = PipelineContext.builder().schemaSourceType(schemaSourceType).build();
            steps.stream().filter(pipeline -> {
                       Order order= pipeline.getClass().getAnnotation(Order.class);
                       return order != null && order.value() >4;
                    }).forEach(pipeline->pipeline.process(context));
        return context;
    }
}
