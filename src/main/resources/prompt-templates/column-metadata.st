Generate business metadata for the database table below.

Schema:
{schemaName}

Table:
{tableName}

INPUT COLUMNS:
{columns}

Generate business metadata for EVERY column in the input.

For each input column generate:
- schemaName
- tableName
- columnName
- businessName
- description
- businessKeywords

Rules:

1. Process every column in the input.
2. Generate exactly one metadata object for each input column.
3. columnName must exactly match the input columnName.
4. schemaName must exactly match the input schemaName.
5. tableName must exactly match the input tableName.
6. Do not omit columns.
7. Do not invent columns.
8. Do not rename columns.
9. businessName must be business-friendly.
10. description must explain the likely business meaning.
11. businessKeywords must contain relevant business terms and synonyms.
12. Use dataType, nullable, and primaryKey when determining meaning.
13. Do not invent unsupported business facts.
14. businessKeywords must be an array of strings.
15. Return ONLY valid JSON.
16. Do not return markdown.
17. Do not return explanations.
18. Do not generate SQL.

The output must be a JSON array.
The number of output objects must equal the number of input columns.