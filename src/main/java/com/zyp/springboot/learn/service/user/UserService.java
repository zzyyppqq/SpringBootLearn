package com.zyp.springboot.learn.service.user;


import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.user.LoginUserDetails;
import com.zyp.springboot.learn.dto.user.UserLoginReq;
import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import com.zyp.springboot.learn.repository.UserDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BcryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService extends BaseService<UserDao, UserEntity> {

    public RespDTO<LoginUserDetails> login(UserLoginReq req) {
        var resp = new LoginUserDetails();
        // 按用户名从数据库查找用户
        UserEntity userEntity = getBaseMapper().getByUsername(req.getUsername());
        // 校验密码
        if (userEntity != null && BcryptUtils.matches(req.getPassword(), userEntity.getPassword())) {
            if (userEntity.getDisabled()) {
                return RespDTO.error(SystemErrorCode.USER_STATUS_ERROR);
            }
            resp.getLoginUserBasicInfo().fill(userEntity);
            return RespDTO.ok(resp);
        } else {
            return RespDTO.paramError("用户名或者密码错误");
        }
    }
}
