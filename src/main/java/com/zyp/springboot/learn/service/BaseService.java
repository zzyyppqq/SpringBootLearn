package com.zyp.springboot.learn.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    @Override
    public T getById(Serializable id) {
        Assert.notNull(id, "id cannot be null");
        return getBaseMapper().selectById(id);
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return getBaseMapper().selectBatchIds(idList);
    }

}
