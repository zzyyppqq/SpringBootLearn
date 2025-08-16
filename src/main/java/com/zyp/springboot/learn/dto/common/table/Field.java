package com.zyp.springboot.learn.dto.common.table;

import com.zyp.springboot.learn.constant.PermissionType;
import com.zyp.springboot.learn.constant.TableFieldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 后续优化：改为字段的注解
@Data
@Builder
@AllArgsConstructor
public class Field {
    /**
     * 前端表格列展示的标题
     */
    private String title = "";
    /**
     * 前端表格列的数据来源字段
     */
    private String dataIndex = "";
    /**
     * 字段类型，会影响前端的展示，编辑和搜索等行为，默认是字符串
     */
    private TableFieldType type = TableFieldType.String;
    /**
     * 该字段是否要作为一列展示在表格中
     */
    private boolean canShow = true;
    /**
     * 该字段是否允许在编辑记录页面中供用户输入，比如自增Id字敦就不需要用户输入
     */
    private boolean canEdit = false;
    /**
     * 该字段是否允许在新增记录页面中供用户输入，比如自增Id字敦就不需要用户输入
     */
    private boolean canAdd = true;
    /**
     * 该字段是否支持搜索
     */
    private boolean searchable = false;
    /**
     * 字段的编辑规则，比如格式，必填等
     */
    private List<FieldRule> rules = new ArrayList<>();
    /**
     * 如果是TableFieldType.EnumNumber或者TableFieldType.EnumString，
     * 这里定义前端可选的标签/值对
     */
    private List<SelectOption> options = new ArrayList<>();
    /**
     * 如果是TableFieldType.Association，
     * 这里定义关联数据的相关信息
     */
    private Association association;

    public Field() {
    }

    public Field(String title, String dataIndex) {
        this.title = title;
        this.dataIndex = dataIndex;
    }

    public Field withType(TableFieldType type) {
        setType(type);
        return this;
    }

    public Field withCanEdit(boolean canEdit) {
        setCanEdit(canEdit);
        return this;
    }

    public Field withCanShow(boolean canShow) {
        setCanShow(canShow);
        return this;
    }

    public Field withCanAdd(boolean canAdd) {
        setCanAdd(canAdd);
        return this;
    }

    public Field withRule(FieldRule rule) {
        getRules().add(rule);
        return this;
    }

    public Field withSearchable(boolean searchable) {
        setSearchable(searchable);
        return this;
    }

    public Field withOptions(SelectOption... options) {
        this.options.addAll(List.of(options));
        return this;
    }

    public Field withAssociation(Association association) {
        this.association = association;
        return this;
    }

}
