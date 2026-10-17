package com.nl2sql.sql.intent.model;


import java.util.List;

public record QueryIntent(IntentType intentType, String businessDomain, List<String> businessKeywords,
                          List<SelectIntent> selections, List<AggregationIntent> aggregations,
                          List<FilterIntent> filters, List<String> groupBy, List<SortIntent> sorting,
                          PaginationIntent pagination, TimeRangeIntent timeRangeIntent) {
}
