package com.zyp.springboot.learn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyp.springboot.learn.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {
}
