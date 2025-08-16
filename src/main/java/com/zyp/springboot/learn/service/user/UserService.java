package com.zyp.springboot.learn.service.user;


import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.repository.UserDao;
import com.zyp.springboot.learn.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService extends BaseService<UserDao, UserEntity> {


}
