package com.zyp.springboot.learn.constant;

public enum ModuleName {
    None(""),
    ROLE("roles"),
    PERMISSION("permissions"),
    MENU("menus"),
    USER("users");
    private final String value;

    ModuleName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
