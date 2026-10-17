package com.nl2sql.schema.parser;

import com.nl2sql.schema.model.*;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SqlSchemaParser implements SchemaParser {

    @Override
    public DatabaseSchema parse(String ddl) {

        Statements statements = parseStatements(ddl);

        CreateTable createTable = statements.getStatements().stream().filter(CreateTable.class::isInstance).map(CreateTable.class::cast).findFirst().orElseThrow(() -> new IllegalArgumentException("DDL must contain CREATE TABLE"));

        Table table = createTable.getTable();

        String schemaName = table.getSchemaName();

        TableProperty tableInfo = prepareTable(createTable);

        return DatabaseSchema.builder().schemaName(schemaName).tableInfo(tableInfo).build();
    }

    private Statements parseStatements(String ddl) {

        if (ddl == null || ddl.isBlank()) {
            throw new IllegalArgumentException("DDL must not be null or empty");
        }

        try {
            return CCJSqlParserUtil.parseStatements(ddl);
        } catch (JSQLParserException e) {
            throw new IllegalArgumentException("Unable to parse DDL", e);
        }
    }

    private TableProperty prepareTable(CreateTable createTable) {

        String tableName = createTable.getTable().getName();

        List<ColumnProperty> columns = prepareColumns(createTable);

        List<RelationProperty> relations = prepareRelations(createTable);

        return TableProperty.builder().tableName(tableName).columns(columns).relation(relations).build();
    }

    /*
     * ---------------------------------------------------------
     * COLUMNS
     * ---------------------------------------------------------
     */

    private List<ColumnProperty> prepareColumns(CreateTable createTable) {

        List<String> primaryKeyColumns = findPrimaryKeyColumns(createTable);

        return Optional.ofNullable(createTable.getColumnDefinitions()).orElse(List.of()).stream().map(column -> {

            String columnName = clean(column.getColumnName());

            String dataType = column.getColDataType().getDataType();

            boolean primaryKey = isColumnLevelPrimaryKey(column) || primaryKeyColumns.stream().anyMatch(pk -> pk.equalsIgnoreCase(columnName));

            boolean nullable = !isNotNull(column);

            return new ColumnProperty(columnName, dataType, nullable, primaryKey);
        }).toList();
    }

    /*
     * Find:
     *
     * CONSTRAINT xxx PRIMARY KEY (ORDER_MATERIAL_ID)
     */
    private List<String> findPrimaryKeyColumns(CreateTable createTable) {

        return Optional.ofNullable(createTable.getIndexes()).orElse(List.of()).stream().filter(this::isPrimaryKey).flatMap(index -> getIndexColumns(index).stream()).map(this::clean).toList();
    }

    private boolean isPrimaryKey(Index index) {

        String type = Optional.ofNullable(index.getType()).orElse("");

        return "PRIMARY KEY".equalsIgnoreCase(type);
    }

    private boolean isColumnLevelPrimaryKey(ColumnDefinition column) {

        return column.toString().toUpperCase().contains("PRIMARY KEY");
    }

    private boolean isNotNull(ColumnDefinition column) {

        return column.toString().toUpperCase().contains("NOT NULL");
    }

    /*
     * ---------------------------------------------------------
     * RELATIONSHIPS
     * ---------------------------------------------------------
     */

    private List<RelationProperty> prepareRelations(CreateTable createTable) {

        String fromTable = createTable.getTable().getName();

        return Optional.ofNullable(createTable.getIndexes()).orElse(List.of()).stream().filter(ForeignKeyIndex.class::isInstance).map(ForeignKeyIndex.class::cast).map(fk -> createRelation(createTable, fromTable, fk)).flatMap(Optional::stream).toList();
    }

    private Optional<RelationProperty> createRelation(CreateTable createTable, String fromTable, ForeignKeyIndex fk) {

        List<String> fromColumns = getIndexColumns(fk);

        List<String> toColumns = Optional.ofNullable(fk.getReferencedColumnNames()).orElse(List.of());

        if (fromColumns.isEmpty() || toColumns.isEmpty()) {
            return Optional.empty();
        }

        Table referencedTable = fk.getTable();

        if (referencedTable == null) {
            return Optional.empty();
        }

        String toTable = referencedTable.getFullyQualifiedName();

        /*
         * Currently handle single-column FK.
         *
         * For composite FK, create one RelationInfo
         * per column pair or introduce a composite relation model.
         */
        if (fromColumns.size() != toColumns.size()) {
            return Optional.empty();
        }

        if (fromColumns.size() != 1) {
            return Optional.empty();
        }

        String fromColumn = clean(fromColumns.get(0));

        String toColumn = clean(toColumns.get(0));

        RelationType relationType = determineRelationType(createTable, fromColumn);

        return Optional.of(RelationProperty.builder().sourceTable(fromTable).sourceColumn(fromColumn).targetTable(toTable).targetColumn(toColumn).relationshipType(relationType).build());
    }

    /*
     * ---------------------------------------------------------
     * RELATION TYPE
     * ---------------------------------------------------------
     */

    private RelationType determineRelationType(CreateTable createTable, String foreignKeyColumn) {

        boolean unique = isUniqueColumn(createTable, foreignKeyColumn);

        return unique ? RelationType.ONE_TO_ONE : RelationType.MANY_TO_ONE;
    }

    private boolean isUniqueColumn(CreateTable createTable, String columnName) {

        String target = clean(columnName);

        /*
         * Column-level UNIQUE
         */
        boolean columnUnique = Optional.ofNullable(createTable.getColumnDefinitions()).orElse(List.of()).stream().anyMatch(column -> {

            if (!clean(column.getColumnName()).equalsIgnoreCase(target)) {
                return false;
            }

            return column.toString().toUpperCase().contains("UNIQUE");
        });

        if (columnUnique) {
            return true;
        }

        /*
         * Table-level UNIQUE
         */
        return Optional.ofNullable(createTable.getIndexes()).orElse(List.of()).stream().filter(this::isUnique).anyMatch(index -> getIndexColumns(index).stream().anyMatch(column -> clean(column).equalsIgnoreCase(target)));
    }

    private boolean isUnique(Index index) {

        String type = Optional.ofNullable(index.getType()).orElse("");

        return "UNIQUE".equalsIgnoreCase(type);
    }

    /*
     * ---------------------------------------------------------
     * INDEX COLUMNS
     * ---------------------------------------------------------
     */

    private List<String> getIndexColumns(Index index) {

        /*
         * JSqlParser versions supporting getColumnsNames()
         */
        return Optional.ofNullable(index.getColumnsNames()).orElse(List.of());
    }

    /*
     * ---------------------------------------------------------
     * UTILITY
     * ---------------------------------------------------------
     */

    private String clean(String value) {

        if (value == null) {
            return null;
        }

        return value.replace("\"", "").trim();
    }
}