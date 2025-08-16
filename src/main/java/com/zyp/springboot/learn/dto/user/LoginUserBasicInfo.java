package com.zyp.springboot.learn.dto.user;

import com.zyp.springboot.learn.entity.UserEntity;
import lombok.Data;

@Data
public class LoginUserBasicInfo {
    private Long uid;
    private String token;
    private String username;
    private String nickName;
    private boolean disabled;

    public void fill(UserEntity user) {
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.disabled = user.getDisabled();
        this.nickName = user.getNickName();
    }
}
