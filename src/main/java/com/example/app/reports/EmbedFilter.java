package com.example.app.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmbedFilter {
    @JsonProperty("$schema")
    private String schema = "http://powerbi.com/product/schema#basic";
    private EmbedFilterTarget target;
    private String operator;
    private List<Object> values;
    private int filterType = 1;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public EmbedFilterTarget getTarget() {
        return target;
    }

    public void setTarget(EmbedFilterTarget target) {
        this.target = target;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }
}
