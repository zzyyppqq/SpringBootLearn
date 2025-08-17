package com.zyp.springboot.learn.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.auth.role_permission.RolePermissionAddReq;
import com.zyp.springboot.learn.dto.auth.role_permission.RolePermissionQueryReq;
import com.zyp.springboot.learn.dto.auth.role_permission.RolePermissionUpdateReq;
import com.zyp.springboot.learn.dto.common.AdjustReq;
import com.zyp.springboot.learn.entity.RolePermissionEntity;
import com.zyp.springboot.learn.repository.RolePermissionDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class RolePermissionService extends BaseService<RolePermissionDao, RolePermissionEntity> {
    public boolean add(RolePermissionAddReq req) {
        return this.save(BeanUtils.copy(req, getEntityClass()));
    }

    @Transactional
    public RespDTO<Boolean> batchOperation(Long roleId, AdjustReq req) {
        var added = Arrays.stream(req.getAdded()).map(id -> new RolePermissionEntity(roleId, id)).toList();
        var deleted = Arrays.stream(req.getDeleted()).map(id -> new RolePermissionEntity(roleId, id)).toList();
        for (RolePermissionEntity entity : added) {
            this.getBaseMapper().insert(entity);
        }
        for (RolePermissionEntity entity : deleted) {
            LambdaQueryWrapper<RolePermissionEntity> param = Wrappers.lambdaQuery();
            param.eq(RolePermissionEntity::getRoleId, entity.getRoleId());
            param.eq(RolePermissionEntity::getPermissionId, entity.getPermissionId());
            this.getBaseMapper().delete(param);
        }
        return RespDTO.ok();
    }

    public IPage<RolePermissionEntity> query(RolePermissionQueryReq req) {
        LambdaQueryWrapper<RolePermissionEntity> param = Wrappers.lambdaQuery();

        if (req.getId() != null) {
            param.eq(RolePermissionEntity::getId, req.getId());
        }
        if (req.getPermissionId() != null) {
            param.eq(RolePermissionEntity::getPermissionId, req.getPermissionId());
        }
        if (req.getRoleId() != null) {
            param.eq(RolePermissionEntity::getRoleId, req.getRoleId());
        }
        param.orderByDesc(RolePermissionEntity::getCreateTime);
        var page = Page.<RolePermissionEntity>of(req.getPage(), req.getSize());
        return this.page(page, param);
    }

    public boolean update(RolePermissionUpdateReq req) {
        var entity = BeanUtils.copy(req, getEntityClass());
        return this.updateById(entity);
    }

    public List<RolePermissionEntity> getByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<RolePermissionEntity> param = Wrappers.lambdaQuery();
        param.in(RolePermissionEntity::getRoleId, roleIds);
        return this.getBaseMapper().selectList(param);
    }
}
