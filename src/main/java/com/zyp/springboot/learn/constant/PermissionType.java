package com.zyp.springboot.learn.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PermissionType {
    WebFunction(SelectOption.WebFunction), // 前端的功能点权限
    Api(SelectOption.Api), // 后端的API权限
    Menu(SelectOption.Menu); // 菜单权限

    @EnumValue
    @JsonValue
    private final int type;

    PermissionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static class SelectOption {
        public static final String LabelWebFunction = "1-前端功能点(WebFunction)";
        public static final int WebFunction = 1;
        public static final String LabelApi = "2-后端接口(Api)";
        public static final int Api = 2;
        public static final String LabelMenu = "3-前端菜单(Menu)";
        public static final int Menu = 3;
    }
}
