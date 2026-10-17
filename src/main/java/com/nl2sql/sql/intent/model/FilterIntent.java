package com.nl2sql.sql.intent.model;


public record FilterIntent(
        FilterOperator operator,
        String field,
        String value

) {
}
