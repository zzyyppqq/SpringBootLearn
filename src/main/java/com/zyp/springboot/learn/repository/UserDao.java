package com.zyp.springboot.learn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyp.springboot.learn.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    /**
     * getByUsername对应的mapper存放在resources/mapper/user/user_mapper.xml文件中
     */
    UserEntity getByUsername(String username);
}
