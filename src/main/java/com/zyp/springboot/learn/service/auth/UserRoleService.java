package com.zyp.springboot.learn.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.auth.user_role.UserRoleAddReq;
import com.zyp.springboot.learn.dto.auth.user_role.UserRoleQueryReq;
import com.zyp.springboot.learn.dto.auth.user_role.UserRoleUpdateReq;
import com.zyp.springboot.learn.dto.common.AdjustReq;
import com.zyp.springboot.learn.entity.UserRoleEntity;
import com.zyp.springboot.learn.repository.UserRoleDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleService extends BaseService<UserRoleDao, UserRoleEntity> {
    public boolean add(UserRoleAddReq req) {
        return this.save(BeanUtils.copy(req, getEntityClass()));
    }

    public IPage<UserRoleEntity> query(UserRoleQueryReq req) {
        LambdaQueryWrapper<UserRoleEntity> param = Wrappers.lambdaQuery();

        if (req.getId() != null) {
            param.eq(UserRoleEntity::getId, req.getId());
        }
        if (req.getUid() != null) {
            param.eq(UserRoleEntity::getUid, req.getUid());
        }
        if (req.getRoleId() != null) {
            param.eq(UserRoleEntity::getRoleId, req.getRoleId());
        }
        param.orderByDesc(UserRoleEntity::getCreateTime);
        var page = Page.<UserRoleEntity>of(req.getPage(), req.getSize());
        return this.page(page, param);
    }

    public boolean update(UserRoleUpdateReq req) {
        var entity = BeanUtils.copy(req, UserRoleEntity.class);
        return this.updateById(entity);
    }

    @Transactional
    public RespDTO<Boolean> batchOperation(Long uid, AdjustReq req) {
        var added = Arrays.stream(req.getAdded()).map(id -> new UserRoleEntity(uid, id)).toList();
        var deleted = Arrays.stream(req.getDeleted()).map(id -> new UserRoleEntity(uid, id)).toList();
        for (UserRoleEntity entity : added) {
            this.save(entity);
        }
        for (UserRoleEntity entity : deleted) {
            LambdaQueryWrapper<UserRoleEntity> param = Wrappers.lambdaQuery();
            param.eq(UserRoleEntity::getUid, entity.getUid());
            param.eq(UserRoleEntity::getRoleId, entity.getRoleId());
            this.remove(param);
        }
        return RespDTO.ok();
    }

    public List<UserRoleEntity> getByUidList(List<Long> uidList) {
        if (CollectionUtils.isEmpty(uidList)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<UserRoleEntity> param = Wrappers.lambdaQuery();
        param.in(UserRoleEntity::getUid, uidList);
        return this.list(param);
    }
}
