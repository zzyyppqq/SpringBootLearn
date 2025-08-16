package com.zyp.springboot.learn.dto.common.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AbstractListResult<T> implements IListResult<T> {
    protected List<T> data = new ArrayList<>();
    protected List<Field> fields = new ArrayList<>();
    protected Action action = new Action();
    protected long total;

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public long getTotal() {
        return total;
    }

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public Action getAction() {
        return action;
    }

    public void addItem(T item) {
        this.data.add(item);
    }
}
