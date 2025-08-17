package com.zyp.springboot.learn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyp.springboot.learn.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {
}
