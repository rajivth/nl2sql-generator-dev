package com.nl2sql.common;

public interface PromptBuilder<I,O> {
    O build(I input);
}
