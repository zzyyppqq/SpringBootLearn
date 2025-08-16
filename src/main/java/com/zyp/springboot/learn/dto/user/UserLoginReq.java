package com.zyp.springboot.learn.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zyp.springboot.learn.constant.RegExpConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserLoginReq {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名最多20字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegExpConst.PASSWORD, message = "请输入6-15位密码(数字|大小写字母|小数点)")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
