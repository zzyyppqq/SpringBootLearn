package com.zyp.springboot.learn.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateReq {
    @NotNull(message = "用户Id不能为空")
    private Long id;
    @Length(max = 20, message = "用户昵称最多20字符")
    private String nickName;
    private Boolean disabled;
}
