Generate business relationship metadata for the database schema below.

INPUT:
{relationshipJson}

The input contains relationships that have already been identified by the backend.

TASK:

Generate business metadata for EVERY relationship in relationInfos.

IMPORTANT:

- Process EVERY relationship in relationInfos.
- Generate exactly ONE output object for EACH input relationship.
- Do not stop after the first relationship.
- Do not omit any relationship.
- Do not combine multiple relationships into one object.
- Do not invent relationships.
- Do not modify table names.
- Do not modify column names.
- Do not modify the relationship type.
- The backend has already determined the relationship.
- Your task is ONLY to generate business metadata for the provided relationships.

For EACH relationship, generate these fields:

sourceTable
sourceColumn
targetTable
targetColumn
relationshipType
businessName
description
businessKeywords

Rules:

1. sourceTable MUST exactly match the input fromTable.
2. sourceColumn MUST exactly match the input fromColumn.
3. targetTable MUST exactly match the input toTable.
4. targetColumn MUST exactly match the input toColumn.
5. relationshipType MUST exactly match the input type.
6. Do not change relationship direction.
7. Do not invent tables.
8. Do not invent columns.
9. businessName MUST be meaningful and business-friendly.
10. description MUST explain the likely business meaning of the relationship.
11. businessKeywords MUST contain relevant business terms, synonyms, and phrases.
12. businessKeywords MUST always be a JSON array of strings.
13. businessKeywords MUST NOT be null.
14. Do not generate SQL.
15. Do not generate additional fields.

OUTPUT REQUIREMENTS:

Return ONLY a valid JSON array.

Each array element must be a JSON object containing exactly these fields:

sourceTable
sourceColumn
targetTable
targetColumn
relationshipType
businessName
description
businessKeywords

The number of output objects MUST equal the number of input relationships.

Do not return markdown.
Do not return explanations.
Do not return any text before or after the JSON.n