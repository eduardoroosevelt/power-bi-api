package com.example.app.common;

public final class Enums {
    private Enums() {
    }

    public enum ResourceType {
        PAGE,
        POWERBI_REPORT,
        EXTERNAL_LINK
    }

    public enum ValueType {
        STRING,
        INT,
        UUID
    }

    public enum SubjectType {
        USER,
        GROUP
    }

    public enum PolicyEffect {
        ALLOW_ALL,
        ALLOW_LIST
    }

    public enum ValuesMode {
        STATIC,
        FROM_USER_ATTRIBUTE
    }

    public enum RuleOperator {
        IN
    }
}
