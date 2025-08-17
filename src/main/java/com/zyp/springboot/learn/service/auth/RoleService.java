package com.zyp.springboot.learn.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.dto.auth.role.RoleAddReq;
import com.zyp.springboot.learn.dto.auth.role.RoleQueryReq;
import com.zyp.springboot.learn.dto.auth.role.RoleUpdateReq;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.entity.RolePermissionEntity;
import com.zyp.springboot.learn.repository.RoleDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BeanUtils;
import com.zyp.springboot.learn.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RoleService extends BaseService<RoleDao, RoleEntity> {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;

    public boolean add(RoleAddReq req) {
        return this.save(BeanUtils.copy(req, getEntityClass()));
    }

    public IPage<RoleEntity> query(RoleQueryReq req) {
        LambdaQueryWrapper<RoleEntity> param = Wrappers.lambdaQuery();

        if (req.getId() != null) {
            param.eq(RoleEntity::getId, req.getId());
        }
        if (StrUtils.hasLength(req.getName())) {
            param.like(RoleEntity::getName, req.getName());
        }
        if (StrUtils.hasLength(req.getDescription())) {
            param.like(RoleEntity::getDescription, req.getDescription());
        }
        param.orderByDesc(RoleEntity::getCreateTime);
        var page = Page.<RoleEntity>of(req.getPage(), req.getSize());
        return this.page(page, param);
    }

    public boolean update(RoleUpdateReq req) {
        return updateById(BeanUtils.copy(req, getEntityClass()));
    }

    public List<PermissionEntity> permissions(Collection<Long> roleIdList) {
        var rolePermissions = rolePermissionService.getByRoleIds(roleIdList);
        var permissionIdList = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).toList();
        return permissionService.listByIds(permissionIdList);
    }
}
