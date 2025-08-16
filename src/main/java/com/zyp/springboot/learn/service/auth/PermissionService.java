package com.zyp.springboot.learn.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.dto.auth.permission.PermissionAddReq;
import com.zyp.springboot.learn.dto.auth.permission.PermissionQueryReq;
import com.zyp.springboot.learn.dto.auth.permission.PermissionUpdateReq;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.repository.PermissionDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BeanUtils;
import com.zyp.springboot.learn.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class PermissionService extends BaseService<PermissionDao, PermissionEntity> {
    // 新增权限：逻辑很简单，入参转换为实体类，然后调用save插入到数据库即可
    public boolean add(PermissionAddReq req) {
        return this.save(BeanUtils.copy(req, getEntityClass()));
    }

    // 列表查询
    public IPage<PermissionEntity> query(PermissionQueryReq req) {
        LambdaQueryWrapper<PermissionEntity> param = Wrappers.lambdaQuery();
        if (req.getId() != null) {
            param.eq(PermissionEntity::getId, req.getId());
        }
        if (StrUtils.hasLength(req.getName())) {
            param.like(PermissionEntity::getName, req.getName());
        }
        if (StrUtils.hasLength(req.getType())) {
            param.eq(PermissionEntity::getType, req.getType());
        }
        param.orderByDesc(PermissionEntity::getCreateTime);
        var page = Page.<PermissionEntity>of(req.getPage(), req.getSize());
        return this.page(page, param);
    }

    // 更新记录
    public boolean update(PermissionUpdateReq req) {
        Assert.notNull(req.getId(), "id is required");
        var entity = BeanUtils.copy(req, PermissionEntity.class);
        return this.getBaseMapper().updateById(entity) == 1;
    }

    // 删除在BaseService中已经提供了
}
