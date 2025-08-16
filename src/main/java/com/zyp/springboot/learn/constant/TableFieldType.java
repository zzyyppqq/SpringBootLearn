package com.zyp.springboot.learn.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TableFieldType {
    String("string"),
    Boolean("boolean"),
    Time("time"),
    Password("password"),
    Association("association"),
    EnumNumber("enum_number"),
    EnumString("enum_string");
    @JsonValue
    private final String type;

    TableFieldType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
