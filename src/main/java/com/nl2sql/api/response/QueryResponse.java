package com.nl2sql.api.response;


public record QueryResponse(
        String question,
        String sql

) {
}
