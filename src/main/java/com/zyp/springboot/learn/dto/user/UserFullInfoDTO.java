package com.zyp.springboot.learn.dto.user;

import com.zyp.springboot.learn.dto.auth.permission.PermissionDTO;
import com.zyp.springboot.learn.dto.auth.role.RoleDTO;
import com.zyp.springboot.learn.dto.menu.MenuTree;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserFullInfoDTO {
    private Long uid;
    private String username;
    private String nickName;
    private List<PermissionDTO> permissions = new ArrayList<>();
    private List<RoleDTO> roles = new ArrayList<>();
    private MenuTree menuTree = new MenuTree();

    public static UserFullInfoDTO currentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (!(authentication.getDetails() instanceof UserFullInfoDTO)) {
            return null;
        }
        return (UserFullInfoDTO) authentication.getDetails();
    }
}
