Generate an Oracle SQL query from the user QUESTION and resolved database metadata below.

IMPORTANT RULES:

1. Use ONLY tables listed in TABLES.
2. Use ONLY columns listed in COLUMNS.
3. Use ONLY relationships listed in RELATIONSHIPS.
4. Never invent table names.
5. Never invent column names.
6. Never invent JOIN conditions.
7. Map business meaning in the question to physical columns from COLUMNS using businessName, columnName, description, and the related table businessName.
8. If the question asks for a business object rather than a specific column, select the available columns belonging to that table.
9. Do NOT select columns from a table unless those columns are present in COLUMNS.
10. Use a JOIN only when required to answer the question and a valid relationship exists in RELATIONSHIPS.
11. Resolve schema-qualified table names exactly as provided.
12. For Oracle, use the physical column names exactly as provided.
13. If the question asks for top N, first N, or limit N, apply Oracle pagination:

    * OFFSET 0 ROWS
    * FETCH NEXT <N> ROWS ONLY
14. Do not add filters, aggregations, GROUP BY, ORDER BY, or JOINs unless they are explicitly required by the question.
15. If no explicit sorting is requested, do not invent ORDER BY.
16. If a business attribute cannot be resolved to a column in COLUMNS, do not invent a column. Omit that attribute.
17. Return ONLY executable Oracle SQL.
18. Do not return markdown, explanations, comments, or code fences.
19. Do NOT use PostgreSQL-specific syntax such as LIMIT, OFFSET ... LIMIT, :: casting, or PostgreSQL functions.
20. Use standard Oracle SQL syntax and Oracle-compatible functions only.
21. When pagination is present and no ORDER BY is specified, still apply OFFSET/FETCH exactly as requested; do not invent an ORDER BY clause.
22. If TABLES is empty, return exactly: -- NO_SCHEMA_MATCH
