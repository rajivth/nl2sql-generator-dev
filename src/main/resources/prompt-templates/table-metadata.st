Generate business metadata for the following database table.

Schema Name:
{schemaName}

Table Name:
{tableName}

Return ONLY valid JSON matching this structure:

\{
  "schemaName": "{schemaName}",
  "tableName": "{tableName}",
  "businessName": "",
  "description": "",
  "businessKeywords": []
\}

Rules:
1. schemaName must be exactly the provided schema name.
2. tableName must be exactly the provided table name.
3. businessName must be a meaningful business-friendly name.
4. description must explain the business purpose of the table.
5. businessKeywords must contain relevant business terms that users may use
   when referring to this table.
6. Include synonyms and common terminology in businessKeywords.
7. Do not invent database columns or relationships.
8. Do not generate SQL.
9. Return ONLY valid JSON.