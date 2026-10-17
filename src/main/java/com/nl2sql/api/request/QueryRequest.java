package com.nl2sql.api.request;

import jakarta.validation.constraints.NotBlank;

public record QueryRequest(@NotBlank(message = "Question cannot be empty") String question) {
}